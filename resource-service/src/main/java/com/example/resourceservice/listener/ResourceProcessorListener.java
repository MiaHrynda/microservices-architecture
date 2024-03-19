package com.example.resourceservice.listener;

import com.example.resourceservice.enums.ResourceProcessingStatus;
import com.example.resourceservice.service.ResourceService;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.TextMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@AllArgsConstructor
public class ResourceProcessorListener {
    private final ResourceService resourceService;

    @JmsListener(destination = "${jms.queue.resourceProcessing}")
    @Transactional
    public void onResourceProcessingMessage(Message message) {
        try {
            log.info("Processing resource processing message.");
            ResourceProcessingMessage processingMessage = convertTextMessage((TextMessage) message);
            if (processingMessage.getStatus() == ResourceProcessingStatus.SUCCESS) {
                resourceService.moveResourceToPermanentStorage(processingMessage.getResourceID());
            } else {
                log.error("Resource processing failed. Status: {}", processingMessage.getStatus());
            }
        } catch (JMSException e) {
            throw new RuntimeException("Issue during processing resource processing message");
        }
    }

    private ResourceProcessingMessage convertTextMessage(TextMessage message) throws JMSException {
        String[] content = message.getText().split(",");
        return new ResourceProcessingMessage(Integer.parseInt(content[0]), ResourceProcessingStatus.valueOf(content[1]));
    }
}
