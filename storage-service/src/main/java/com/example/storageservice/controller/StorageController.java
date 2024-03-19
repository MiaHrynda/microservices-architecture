package com.example.storageservice.controller;

import com.example.storageservice.dto.StorageObjectDTO;
import com.example.storageservice.dto.StorageObjectLightDTO;
import com.example.storageservice.dto.StorageObjectRemovalDTO;
import com.example.storageservice.service.StorageService;
import com.example.storageservice.validation.ValidCSVLength;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(StorageController.BASE_URL)
@RequiredArgsConstructor
public class StorageController {
    static final String BASE_URL = "/storages";
    private final StorageService storageService;

    @PostMapping
    public ResponseEntity<StorageObjectLightDTO> createStorage(@RequestBody StorageObjectDTO storageObjectDTO) {
        return ResponseEntity.ok(storageService.createStorage(storageObjectDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StorageObjectDTO> getStorage(@PathVariable Integer id) {
        return ResponseEntity.ok(storageService.getStorage(id));
    }

    @GetMapping
    public ResponseEntity<List<StorageObjectDTO>> getStorages() {
        return ResponseEntity.ok(storageService.getStorages());
    }

    @DeleteMapping
    public ResponseEntity<StorageObjectRemovalDTO> deleteSongsMetadata(@RequestParam @ValidCSVLength String ids) {
        return ResponseEntity.ok(storageService.deleteStorages(ids));
    }
}
