package com.example.resourceservice.service.impl;

import com.example.resourceservice.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class S3ServiceImpl implements S3Service {
    private final S3Client s3Client;

    @Value("${amazonProperties.bucketName}")
    private String bucketName;

    @Override
    public PutObjectResponse uploadResource(String s3Key, byte[] resource) {
        return s3Client.putObject(PutObjectRequest.builder()
                .bucket(bucketName)
                .key(s3Key)
                .build(), RequestBody.fromBytes(resource));
    }

    @Override
    public CopyObjectResponse moveResource(String sourceKey, String destinationKey) {
        CopyObjectResponse response = s3Client.copyObject(CopyObjectRequest.builder()
                .sourceBucket(bucketName)
                .sourceKey(sourceKey)
                .destinationBucket(bucketName)
                .destinationKey(destinationKey)
                .build());

        deleteResource(sourceKey);
        return response;
    }

    @Override
    public byte[] getResource(String s3Key) throws IOException {
        return s3Client.getObject(GetObjectRequest.builder()
                .bucket(bucketName)
                .key(s3Key).build()).readAllBytes();
    }

    @Override
    public void deleteResource(String s3Key) {
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(s3Key).build();
        s3Client.deleteObject(deleteObjectRequest);
    }
}
