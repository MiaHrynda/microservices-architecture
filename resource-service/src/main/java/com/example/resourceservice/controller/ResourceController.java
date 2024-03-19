package com.example.resourceservice.controller;

import com.example.resourceservice.dto.ResourceDTO;
import com.example.resourceservice.dto.ResourcesRemovalDTO;
import com.example.resourceservice.service.ResourceService;
import com.example.resourceservice.validation.ValidCSVLength;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(ResourceController.BASE_URL)
@RequiredArgsConstructor
public class ResourceController {
    static final String BASE_URL = "/resources";
    private static final String AUDIO_MPEG_CONTENT_TYPE = "audio/mpeg";
    private final ResourceService resourceService;

    @PostMapping(consumes = AUDIO_MPEG_CONTENT_TYPE)
    public ResponseEntity<ResourceDTO> uploadResource(@RequestBody byte[] audioData) {
        log.info("uploading resource");
        return ResponseEntity.ok(resourceService.uploadResource(audioData));
    }

    @GetMapping(value = "/{id}", produces = AUDIO_MPEG_CONTENT_TYPE)
    public ResponseEntity<byte[]> getResource(@PathVariable Integer id) {
        log.info("getting resource");
        return ResponseEntity.ok(resourceService.getResource(id));
    }

    @DeleteMapping
    public ResponseEntity<ResourcesRemovalDTO> deleteResources(@RequestParam @ValidCSVLength String ids) {
        return ResponseEntity.ok(resourceService.deleteResources(ids));
    }
}
