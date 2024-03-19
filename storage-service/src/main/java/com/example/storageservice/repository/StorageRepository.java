package com.example.storageservice.repository;

import com.example.storageservice.persistence.StorageObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StorageRepository extends JpaRepository<StorageObject, Integer> {
}
