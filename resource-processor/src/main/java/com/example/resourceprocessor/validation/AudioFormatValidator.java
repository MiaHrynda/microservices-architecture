package com.example.resourceprocessor.validation;

import com.example.resourceprocessor.constants.AudioFormat;
import org.apache.tika.metadata.Metadata;

public class AudioFormatValidator {
    private static final String AUDIO_COMPRESSOR_METADATA = "xmpDM:audioCompressor";

    public static boolean isAudioInMP3Format(Metadata audioMetadata) {
        return audioMetadata.get(AUDIO_COMPRESSOR_METADATA).equals(AudioFormat.MP3.getValue());
    }
}
