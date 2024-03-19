package com.example.songservice.service;

import com.example.songservice.dto.SongDTO;
import com.example.songservice.dto.SongUploadDTO;
import com.example.songservice.dto.SongsRemovalDTO;

public interface SongService {

    SongUploadDTO createOrUpdateSongMetadata(SongDTO songMetadata);

    SongDTO getSongMetadata(Integer id);

    void deleteSongMetadata(Integer id);

    SongsRemovalDTO deleteSongsMetadata(String ids);
}
