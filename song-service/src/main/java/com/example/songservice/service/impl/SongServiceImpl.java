package com.example.songservice.service.impl;

import com.example.songservice.converter.DTOConverter;
import com.example.songservice.dto.SongDTO;
import com.example.songservice.dto.SongUploadDTO;
import com.example.songservice.dto.SongsRemovalDTO;
import com.example.songservice.entity.Song;
import com.example.songservice.repository.SongRepository;
import com.example.songservice.service.SongService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SongServiceImpl implements SongService {
    private final SongRepository songRepository;

    @Override
    public SongUploadDTO createOrUpdateSongMetadata(SongDTO songMetadata) {
        Optional<Song> songOptional = songRepository.getByResourceId(songMetadata.getResourceId());
        log.debug("Saving song metadata to the db");
        if (songOptional.isPresent()) {
            Song song = songOptional.get();
            song.setAlbum(songMetadata.getAlbum());
            song.setName(song.getName());
            song.setLength(song.getLength());
            song.setYear(song.getYear());
            songRepository.save(song);
            return new SongUploadDTO(song.getId());
        } else {
            Song savedEntity = songRepository.save(DTOConverter.songDTOToSongEntity.apply(songMetadata));
            return new SongUploadDTO(savedEntity.getId());
        }
    }

    @Override
    public SongDTO getSongMetadata(Integer id) {
        log.debug("Retrieving song metadata by id {}", id);
        return DTOConverter.songEntityToSongDTO.apply(songRepository.getReferenceById(id));
    }

    @Override
    public void deleteSongMetadata(Integer id) {
        log.debug("Deleting song metadata by id {}", id);
        songRepository.deleteById(id);
    }

    @Override
    public SongsRemovalDTO deleteSongsMetadata(String ids) {
        List<Integer> songsIds = Arrays.stream(ids.split(","))
                .mapToInt(Integer::parseInt)
                .boxed()
                .distinct()
                .toList();
        songsIds.forEach(this::deleteSongMetadata);
        return new SongsRemovalDTO(songsIds);
    }
}
