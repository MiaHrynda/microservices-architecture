package com.example.storageservice.service.impl;

import com.example.storageservice.converter.DTOConverter;
import com.example.storageservice.dto.StorageObjectDTO;
import com.example.storageservice.dto.StorageObjectLightDTO;
import com.example.storageservice.dto.StorageObjectRemovalDTO;
import com.example.storageservice.persistence.StorageObject;
import com.example.storageservice.repository.StorageRepository;
import com.example.storageservice.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StorageServiceImpl implements StorageService {
    private final StorageRepository storageRepository;

    @Override
    public StorageObjectLightDTO createStorage(StorageObjectDTO storageObjectDTO) {
        StorageObject savedEntity = storageRepository.save(DTOConverter.storageDTOToStorageEntity.apply(storageObjectDTO));
        return new StorageObjectLightDTO(savedEntity.getId());
    }

    @Override
    public StorageObjectDTO getStorage(Integer id) {
        return DTOConverter.storageEntityToStorageDTO.apply(storageRepository.getReferenceById(id));
    }

    @Override
    public List<StorageObjectDTO> getStorages() {
        return storageRepository.findAll().stream()
                .map(DTOConverter.storageEntityToStorageDTO)
                .toList();
    }

    @Override
    public void deleteStorage(Integer id) {
        storageRepository.deleteById(id);
    }

    @Override
    public StorageObjectRemovalDTO deleteStorages(String ids) {
        List<Integer> storagesIds = Arrays.stream(ids.split(","))
                .mapToInt(Integer::parseInt)
                .boxed()
                .distinct()
                .toList();
        storagesIds.forEach(this::deleteStorage);
        return new StorageObjectRemovalDTO(storagesIds);
    }
}
