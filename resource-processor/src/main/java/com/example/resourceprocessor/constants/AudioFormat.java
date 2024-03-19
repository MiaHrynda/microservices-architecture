package com.example.resourceprocessor.constants;

import lombok.Getter;

@Getter
public enum AudioFormat {
    MP3("MP3");

    private final String value;

    AudioFormat(String value) {
        this.value = value;
    }
}