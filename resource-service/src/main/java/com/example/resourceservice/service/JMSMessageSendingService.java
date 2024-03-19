package com.example.resourceservice.service;

public interface JMSMessageSendingService {

    void sendTextMessage(String destination, String content);
}
