package com.example.resourceservice.service.impl;

import com.example.resourceservice.dto.*;
import com.example.resourceservice.entity.Resource;
import com.example.resourceservice.enums.ResourceState;
import com.example.resourceservice.exception.ResourceServiceRuntimeException;
import com.example.resourceservice.repository.ResourceRepository;
import com.example.resourceservice.service.*;
import com.example.resourceservice.dto.StorageObjectDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.amazon.awssdk.services.s3.model.CopyObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResourceServiceImpl implements ResourceService {
    private final ResourceRepository resourceRepository;
    private final S3Service s3Service;
    private final JMSMessageSendingService jmsMessageSendingService;
    private final StorageIntegrationService storageIntegrationService;

    @Value("${amazonProperties.bucketName}")
    private String bucketName;

    @Value("${jms.queue.resourceUpload}")
    private String resourceUploadQueue;

    @Override
    @Transactional
    public ResourceDTO uploadResource(byte[] audioData) {
        Integer resourceID = null;
        String s3Key = UUID.randomUUID().toString();

        log.debug("Getting info about staging storage.");
        Optional<StorageObjectDTO> stagingStorage = storageIntegrationService.getStorages().stream()
                .filter(storage -> storage.getBucketName().equals(bucketName)
                        && storage.getStorageType().name().equals(ResourceState.STAGING.name()))
                .findFirst();

        if (stagingStorage.isPresent()) {
            String stagingPath = stagingStorage.get().getPath();
            log.debug("Uploading resource {} to the staging s3 storage.", s3Key);
            PutObjectResponse response = s3Service.uploadResource(stagingPath  + "/" + s3Key, audioData);
            if (response.sdkHttpResponse().isSuccessful()) {
                log.debug("Saving resource location info into db.");
                Resource resource = resourceRepository.save(new Resource(s3Key, stagingPath, ResourceState.STAGING));

                log.debug("Sending jms message to the queue {}.", resourceUploadQueue);
                jmsMessageSendingService.sendTextMessage(resourceUploadQueue, String.valueOf(resource.getId()));
                resourceID = resource.getId();
            } else {
                throw new ResourceServiceRuntimeException("Issue happened during resource upload.");
            }
        }
        log.info("Resource {} was uploaded successfully to the staging storage", resourceID);
        return new ResourceDTO(resourceID);
    }

    public ResourceDTO moveResourceToPermanentStorage(Integer resourceID) {
        Resource stagingResource = resourceRepository.getReferenceById(resourceID);
        String s3Key = stagingResource.getContentUniqueId();

        log.debug("Getting info about permanent storage.");
        Optional<StorageObjectDTO> permanentStorage = storageIntegrationService.getStorages().stream()
                .filter(storage -> storage.getBucketName().equals(bucketName)
                        && storage.getStorageType().name().equals(ResourceState.PERMANENT.name()))
                .findFirst();
        if (permanentStorage.isPresent()) {
            String permanentPath = permanentStorage.get().getPath();
            String stagingPath = stagingResource.getPath();

            log.debug("Moving resource {} from the staging s3 storage to the permanent one.", s3Key);
            CopyObjectResponse response = s3Service.moveResource(stagingPath + "/" + s3Key, permanentPath + "/" + s3Key);
            if (response.sdkHttpResponse().isSuccessful()) {
                stagingResource.setState(ResourceState.PERMANENT);
                stagingResource.setPath(permanentPath);

                log.debug("Saving resource location info into db.");
                resourceRepository.save(stagingResource);
            } else {
                throw new ResourceServiceRuntimeException("Issue happened during resource change location.");
            }
        }
        log.info("Resource {} was moved to the permanent storage successfully", resourceID);
        return new ResourceDTO(resourceID);
    }

    @Override
    public byte[] getResource(Integer id) {
        Resource resource = resourceRepository.getReferenceById(id);
        try {
            log.debug("Getting resource from the s3 permanent storage by id {}", id);
            return s3Service.getResource(resource.getPath() + "/" + resource.getContentUniqueId());
        } catch (IOException e) {
            throw new ResourceServiceRuntimeException("Can not retrieve resource by id " + id);
        }
    }

    @Override
    public void deleteResource(Integer id) {
        Resource resource = resourceRepository.getReferenceById(id);
        log.debug("Deleting resource from the s3 permanent storage by id {}", id);
        s3Service.deleteResource(resource.getPath() + "/" + resource.getContentUniqueId());
        log.debug("Deleting resource info from the db by id {}", id);
        resourceRepository.deleteById(id);
    }

    @Override
    public ResourcesRemovalDTO deleteResources(String ids) {
        List<Integer> resourcesIds = Arrays.stream(ids.split(","))
                .mapToInt(Integer::parseInt)
                .boxed()
                .distinct()
                .toList();
        resourcesIds.forEach(this::deleteResource);
        return new ResourcesRemovalDTO(resourcesIds);
    }
}
