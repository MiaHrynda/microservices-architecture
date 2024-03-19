package com.example.resourceprocessor.service.impl;

import com.example.resourceprocessor.converter.DTOConverter;
import com.example.resourceprocessor.dto.SongDTO;
import com.example.resourceprocessor.enums.ResourceProcessingStatus;
import com.example.resourceprocessor.service.AudioMetadataExtractorService;
import com.example.resourceprocessor.service.ResourceProcessorService;
import com.example.resourceprocessor.utils.HttpClient;
import com.example.resourceprocessor.validation.AudioFormatValidator;
import jakarta.ws.rs.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.metadata.Metadata;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResourceProcessorServiceImpl implements ResourceProcessorService {
    private final AudioMetadataExtractorService audioMetadataExtractor;
    private final RetryTemplate retryTemplate;
    private final JmsTemplate jmsTemplate;
    private final HttpClient httpClient;

    @Value("${jms.queue.resourceProcessing}")
    private String resourceProcessingQueue;

    @Override
    @Transactional
    public void processMP3(byte[] audioData, int resourceID) {
        log.info("MP3 processing started. ResourceID: {}", resourceID);
        Metadata metadata = audioMetadataExtractor.extractAudioMetadata(audioData);
        if (!AudioFormatValidator.isAudioInMP3Format(metadata)) {
            throw new BadRequestException("Audio should be in the MP3 format.");
        }

        SongDTO songMetadata = DTOConverter.metadataToSongDTO.apply(metadata);
        songMetadata.setResourceId(resourceID);

        log.debug("Calling song service to save song metadata.");
        retryTemplate.execute(context -> httpClient.callSongService(songMetadata));

        log.debug("Sending resource processing JMS message to the queue {}", resourceProcessingQueue);
        jmsTemplate.send(resourceProcessingQueue, session ->
                session.createTextMessage(resourceID + "," + ResourceProcessingStatus.SUCCESS));
        log.info("MP3 processing successfully finished. ResourceID: {}", resourceID);
    }
}
