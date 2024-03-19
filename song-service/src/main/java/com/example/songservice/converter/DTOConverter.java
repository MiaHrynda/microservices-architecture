package com.example.songservice.converter;

import com.example.songservice.dto.SongDTO;
import com.example.songservice.entity.Song;

import java.util.function.Function;

public class DTOConverter {

    public static final Function<SongDTO, Song> songDTOToSongEntity =
            (SongDTO dto) -> {
                Song entity = new Song();
                entity.setName(dto.getName());
                entity.setAlbum(dto.getAlbum());
                entity.setArtist(dto.getArtist());
                entity.setLength(dto.getLength());
                entity.setYear(dto.getYear());
                entity.setResourceId(dto.getResourceId());
                return entity;
            };

    public static final Function<Song, SongDTO> songEntityToSongDTO =
            (Song entity) -> {
                SongDTO dto = new SongDTO();
                dto.setId(entity.getId());
                dto.setName(entity.getName());
                dto.setAlbum(entity.getAlbum());
                dto.setArtist(entity.getArtist());
                dto.setLength(entity.getLength());
                dto.setYear(entity.getYear());
                dto.setResourceId(entity.getResourceId());
                return dto;
            };
}
