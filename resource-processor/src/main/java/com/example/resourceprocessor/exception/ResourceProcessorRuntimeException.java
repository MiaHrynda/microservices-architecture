package com.example.resourceprocessor.exception;

public class ResourceProcessorRuntimeException extends RuntimeException {

    public ResourceProcessorRuntimeException(String message) {
        super(message);
    }

    public ResourceProcessorRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
