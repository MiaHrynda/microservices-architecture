package com.example.storageservice.persistence;

import com.example.storageservice.enums.StorageType;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "storage")
public class StorageObject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "storage_type", nullable = false)
    private StorageType storageType;

    @Column(name = "bucket", nullable = false)
    private String bucketName;

    @Column(name = "path", nullable = false)
    private String path;
}