package com.example.songservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class SongsRemovalDTO {
    private List<Integer> ids;
}
