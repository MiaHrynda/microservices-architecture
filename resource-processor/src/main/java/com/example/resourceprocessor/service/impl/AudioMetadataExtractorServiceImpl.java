package com.example.resourceprocessor.service.impl;

import com.example.resourceprocessor.exception.ResourceProcessorRuntimeException;
import com.example.resourceprocessor.service.AudioMetadataExtractorService;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class AudioMetadataExtractorServiceImpl implements AudioMetadataExtractorService {

    @Override
    public Metadata extractAudioMetadata(byte[] audioData) {
        Mp3Parser Mp3Parser = new  Mp3Parser();
        BodyContentHandler contentHandler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        try (InputStream audioStream = new ByteArrayInputStream(audioData)) {
            Mp3Parser.parse(audioStream, contentHandler, metadata, new ParseContext());
        } catch (IOException | SAXException | TikaException e) {
            throw new ResourceProcessorRuntimeException("Problems with audio file metadata extraction. ", e);
        }
        return metadata;
    }
}
