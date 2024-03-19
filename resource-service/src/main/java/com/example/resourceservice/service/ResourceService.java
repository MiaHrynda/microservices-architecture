package com.example.resourceservice.service;

import com.example.resourceservice.dto.ResourceDTO;
import com.example.resourceservice.dto.ResourcesRemovalDTO;

public interface ResourceService {

    ResourceDTO uploadResource(byte[] audioData);

    ResourceDTO moveResourceToPermanentStorage(Integer resourceID);

    byte[] getResource(Integer id);

    void deleteResource(Integer id);

    ResourcesRemovalDTO deleteResources(String ids);
}
