package com.example.resourceservice.listener;

import com.example.resourceservice.enums.ResourceProcessingStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResourceProcessingMessage {
    private Integer resourceID;
    private ResourceProcessingStatus status;
}
