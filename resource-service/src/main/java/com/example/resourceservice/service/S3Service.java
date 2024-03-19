package com.example.resourceservice.service;

import software.amazon.awssdk.services.s3.model.CopyObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.IOException;

public interface S3Service {

    PutObjectResponse uploadResource(String s3Key, byte[] resource);

    CopyObjectResponse moveResource(String sourceKey, String destinationKey);

    byte[] getResource(String s3Key) throws IOException;

    void deleteResource(String s3Key);
}
