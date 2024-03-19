package com.example.resourceprocessor.service;

import org.apache.tika.metadata.Metadata;

public interface AudioMetadataExtractorService {

    Metadata extractAudioMetadata(byte[] audioData);
}
