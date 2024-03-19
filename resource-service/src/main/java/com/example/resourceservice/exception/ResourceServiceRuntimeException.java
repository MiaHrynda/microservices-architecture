package com.example.resourceservice.exception;

public class ResourceServiceRuntimeException extends RuntimeException {

    public ResourceServiceRuntimeException(String message) {
        super(message);
    }

    public ResourceServiceRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
