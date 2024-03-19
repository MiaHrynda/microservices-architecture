package com.example.resourceprocessor.listener;

import com.example.resourceprocessor.service.ResourceProcessorService;
import com.example.resourceprocessor.utils.HttpClient;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.TextMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class ResourceServiceListener {
    private final ResourceProcessorService resourceProcessorService;
    private final RetryTemplate retryTemplate;
    private final HttpClient httpClient;

    @JmsListener(destination = "${jms.subscription.resourceUpload}")
    @Transactional
    public void handleResourceUploadEvent(Message message) throws JMSException {
        String resourceID = ((TextMessage) message).getText();
        log.info("Processing resource {} upload event.", resourceID);
        byte[] resourceContent = retryTemplate.execute(context
                -> httpClient.callResourceService(Integer.valueOf(resourceID)));
        resourceProcessorService.processMP3(resourceContent, Integer.parseInt(resourceID));
    }
}
