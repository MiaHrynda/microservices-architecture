package com.example.resourceservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ResourcesRemovalDTO {
    private List<Integer> ids;
}
