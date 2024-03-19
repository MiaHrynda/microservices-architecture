package com.example.storageservice.dto;

import com.example.storageservice.enums.StorageType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StorageObjectDTO {
    private Integer id;
    private StorageType storageType;
    private String bucketName;
    private String path;
}
