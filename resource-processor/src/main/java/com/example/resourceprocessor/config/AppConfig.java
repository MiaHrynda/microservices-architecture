package com.example.resourceprocessor.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.client.RestClient;

@Configuration
public class AppConfig {
    @Value("${jms.retry.backoff.multiplier}")
    private double multiplier;

    @Value("${jms.retry.backoff.initialInterval}")
    private long initialInterval;

    @Value("${jms.retry.backoff.maxInterval}")
    private long maxInterval;

    @Bean
    RestClient getRestClient() {
        return RestClient.create();
    }

    @Bean
    public RetryTemplate retryTemplate() {
        RetryTemplate retryTemplate = new RetryTemplate();

        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
        backOffPolicy.setMultiplier(multiplier);
        backOffPolicy.setInitialInterval(initialInterval);
        backOffPolicy.setMaxInterval(maxInterval);

        retryTemplate.setBackOffPolicy(backOffPolicy);
        return retryTemplate;
    }

}
