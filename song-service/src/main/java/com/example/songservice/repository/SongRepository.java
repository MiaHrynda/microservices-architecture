package com.example.songservice.repository;

import com.example.songservice.entity.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SongRepository extends JpaRepository<Song, Integer> {
    Optional<Song> getByResourceId(int resourceId);
}
