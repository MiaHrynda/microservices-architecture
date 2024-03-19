package com.example.resourceservice.entity;

import com.example.resourceservice.enums.ResourceState;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "resource")
@NoArgsConstructor
@AllArgsConstructor
public class Resource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "content_unique_id", nullable = false, unique = true)
    private String contentUniqueId;

    @Column(name = "path", nullable = false)
    private String path;

    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private ResourceState state;

    public Resource(String contentUniqueId) {
        this.contentUniqueId = contentUniqueId;
    }

    public Resource(String contentUniqueId, String path, ResourceState state) {
        this.contentUniqueId = contentUniqueId;
        this.path = path;
        this.state = state;
    }

    public Resource(Integer id, String contentUniqueId) {
        this.id = id;
        this.contentUniqueId = contentUniqueId;
    }
}
