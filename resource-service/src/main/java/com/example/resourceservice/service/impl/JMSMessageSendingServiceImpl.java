package com.example.resourceservice.service.impl;

import com.example.resourceservice.service.JMSMessageSendingService;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JMSMessageSendingServiceImpl implements JMSMessageSendingService {
    private final JmsTemplate jmsTemplate;

    @Override
    @Retryable(backoff=@Backoff(delay=15000L, maxDelay=60000L))
    public void sendTextMessage(String destination, String content) {
        jmsTemplate.send(destination, session -> session.createTextMessage(content));
    }
}
