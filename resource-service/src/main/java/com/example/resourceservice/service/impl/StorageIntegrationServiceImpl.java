package com.example.resourceservice.service.impl;

import com.example.resourceservice.client.HttpClient;
import com.example.resourceservice.service.StorageIntegrationService;
import com.example.resourceservice.dto.StorageObjectDTO;
import com.example.resourceservice.enums.StorageType;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class StorageIntegrationServiceImpl implements StorageIntegrationService {
    private static final String STAGING_STORAGE_FALLBACK_PATH = "staging";
    private static final String PERMANENT_STORAGE_FALLBACK_PATH = "permanent";
    
    private final HttpClient httpClient;

    @Value("${amazonProperties.bucketName}")
    private String bucketName;

    @Override
    @CircuitBreaker(name = "getStorages", fallbackMethod = "fallbackForGetStorages")
    public List<StorageObjectDTO> getStorages() {
        return Objects.requireNonNull(httpClient.getStorages());
    }

    public List<StorageObjectDTO> fallbackForGetStorages(Throwable t) {
        log.error("Inside circuit breaker fallbackForGetStorages, cause - {}", t.toString());
        return List.of(new StorageObjectDTO(null, StorageType.STAGING, bucketName, STAGING_STORAGE_FALLBACK_PATH),
                new StorageObjectDTO(null, StorageType.PERMANENT, bucketName, PERMANENT_STORAGE_FALLBACK_PATH));
    }
}
