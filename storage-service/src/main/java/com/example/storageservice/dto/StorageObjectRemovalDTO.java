package com.example.storageservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class StorageObjectRemovalDTO {
    private List<Integer> ids;
}
