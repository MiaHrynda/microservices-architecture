package com.example.songservice.controller;

import com.example.songservice.dto.SongDTO;
import com.example.songservice.dto.SongUploadDTO;
import com.example.songservice.dto.SongsRemovalDTO;
import com.example.songservice.service.SongService;
import com.example.songservice.validation.ValidCSVLength;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(SongController.BASE_URL)
@RequiredArgsConstructor
public class SongController {
    static final String BASE_URL = "/songs";
    private final SongService songService;

    @PostMapping
    public ResponseEntity<SongUploadDTO> createOrUpdateSongMetadata(@RequestBody SongDTO songMetadata) {
        return ResponseEntity.ok(songService.createOrUpdateSongMetadata(songMetadata));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SongDTO> getSongMetadata(@PathVariable Integer id) {
        return ResponseEntity.ok(songService.getSongMetadata(id));
    }

    @DeleteMapping
    public ResponseEntity<SongsRemovalDTO> deleteSongsMetadata(@RequestParam @ValidCSVLength String ids) {
        return ResponseEntity.ok(songService.deleteSongsMetadata(ids));
    }
}
