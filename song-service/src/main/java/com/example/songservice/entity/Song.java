package com.example.songservice.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "song")
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    private String name;

    private String artist;

    private String album;

    private String length;

    @Column(name = "resource_id")
    private Integer resourceId;

    private String year;
}
