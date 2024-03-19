package com.example.resourceservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.retry.RetryPolicy;
import software.amazon.awssdk.core.retry.backoff.FixedDelayBackoffStrategy;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.time.Duration;

@Configuration
public class S3Configuration {

    @Value("${amazonProperties.bucketName}")
    private String bucketName;
    @Value("${amazonProperties.accessKey}")
    private String accessKey;
    @Value("${amazonProperties.secretKey}")
    private String secretKey;

    @Value("${amazonProperties.retries.max.number}")
    private Integer retriesMaxNumber;
    @Value("${amazonProperties.retries.delay}")
    private Integer retriesDelay;

    @Bean
    public S3Client configureS3Client() {
        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(accessKey, secretKey);

        return S3Client.builder()
                .region(Region.US_EAST_1)
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .overrideConfiguration(config -> config.retryPolicy(amazonS3CustomRetryPolicy()))
                .build();
    }

    private RetryPolicy amazonS3CustomRetryPolicy() {
        return RetryPolicy.builder()
                .numRetries(retriesMaxNumber)
                .retryCondition(retryContext -> {
                    Throwable exception = retryContext.exception();
                    if (exception instanceof S3Exception) {
                        int statusCode = ((S3Exception) exception).statusCode();
                        return statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()
                                || statusCode == HttpStatus.SERVICE_UNAVAILABLE.value();
                    }
                    return false;
                })
                .backoffStrategy(FixedDelayBackoffStrategy.create(Duration.ofSeconds(15)))
                .build();
    }
}