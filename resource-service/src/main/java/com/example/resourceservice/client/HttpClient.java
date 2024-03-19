package com.example.resourceservice.client;

import com.example.resourceservice.dto.StorageObjectDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
@RequiredArgsConstructor
public class HttpClient {
    private final RestClient restClient;

    @Value("${storage-service.url}")
    private String storageServiceUrl;
    
    public List<StorageObjectDTO> getStorages() {
        return restClient.get()
                .uri(storageServiceUrl)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
    } 
}
