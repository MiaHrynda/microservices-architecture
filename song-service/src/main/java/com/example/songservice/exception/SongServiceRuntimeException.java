package com.example.songservice.exception;

public class SongServiceRuntimeException extends RuntimeException {

    public SongServiceRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
