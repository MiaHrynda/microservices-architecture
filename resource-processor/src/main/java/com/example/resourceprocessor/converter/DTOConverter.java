package com.example.resourceprocessor.converter;

import com.example.resourceprocessor.dto.SongDTO;
import org.apache.tika.metadata.Metadata;

import java.time.Duration;
import java.util.function.Function;

public class DTOConverter {

    public static final Function<Metadata, SongDTO> metadataToSongDTO =
            (Metadata metadata) -> {
                SongDTO dto = new SongDTO();
                dto.setName(metadata.get("dc:title"));
                dto.setAlbum(metadata.get("xmpDM:album"));
                dto.setArtist(metadata.get("xmpDM:albumArtist"));
                dto.setLength(formatAudioDuration(metadata.get("xmpDM:duration")));
                dto.setYear(metadata.get("xmpDM:releaseDate"));
                return dto;
            };

    private static String formatAudioDuration(String durationMetadata) {
        Duration duration = Duration.ofSeconds((long) Double.parseDouble(durationMetadata));
        long hours = duration.toHours();
        long minutes = duration.minusHours(hours).toMinutes();
        long seconds = duration.minusMinutes(minutes).toSeconds();
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}
