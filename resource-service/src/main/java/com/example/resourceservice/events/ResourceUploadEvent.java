package com.example.resourceservice.events;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class ResourceUploadEvent implements Serializable {
    private Integer resourceID;
}
