package com.example.storageservice.service;

import com.example.storageservice.dto.StorageObjectDTO;
import com.example.storageservice.dto.StorageObjectLightDTO;
import com.example.storageservice.dto.StorageObjectRemovalDTO;

import java.util.List;

public interface StorageService {

    StorageObjectLightDTO createStorage(StorageObjectDTO storageObjectDTO);

    StorageObjectDTO getStorage(Integer id);

    List<StorageObjectDTO> getStorages();

    void deleteStorage(Integer id);

    StorageObjectRemovalDTO deleteStorages(String ids);
}
