package com.example.resourceprocessor.utils;

import com.example.resourceprocessor.dto.SongDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
@RequiredArgsConstructor
public class HttpClient {
    private final RestClient restClient;

    @Value("${resource-service.url}")
    private String resourceServiceUrl;

    @Value("${song-service.url}")
    private String songServiceUrl;

    public byte[] callResourceService(Integer resourceId) {
        return restClient
                .get()
                .uri(resourceServiceUrl + "/" + resourceId)
                .retrieve()
                .body(byte[].class);
    }

    public SongDTO callSongService(SongDTO parsedSong) {
        return restClient.post()
                .uri(songServiceUrl)
                .contentType(APPLICATION_JSON)
                .body(parsedSong)
                .retrieve().body(SongDTO.class);
    }
}
