package com.example.storageservice.converter;

import com.example.storageservice.dto.StorageObjectDTO;
import com.example.storageservice.persistence.StorageObject;

import java.util.function.Function;

public class DTOConverter {

    public static final Function<StorageObjectDTO, StorageObject> storageDTOToStorageEntity =
            (StorageObjectDTO dto) -> {
                StorageObject entity = new StorageObject();
                entity.setStorageType(dto.getStorageType());
                entity.setBucketName(dto.getBucketName());
                entity.setPath(dto.getPath());
                return entity;
            };

    public static final Function<StorageObject, StorageObjectDTO> storageEntityToStorageDTO =
            (StorageObject entity) -> {
                StorageObjectDTO dto = new StorageObjectDTO();
                dto.setId(entity.getId());
                dto.setStorageType(entity.getStorageType());
                dto.setBucketName(entity.getBucketName());
                dto.setPath(entity.getPath());
                return dto;
            };
}
