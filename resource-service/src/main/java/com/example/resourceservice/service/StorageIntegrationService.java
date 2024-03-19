package com.example.resourceservice.service;

import com.example.resourceservice.dto.StorageObjectDTO;

import java.util.List;

public interface StorageIntegrationService {

    List<StorageObjectDTO> getStorages();
}
