package com.cloudstorage.manager;

import com.cloudstorage.model.FileMetadata;

import java.util.HashMap;
import java.util.Map;

public class MetadataManager {

    private final Map<String, FileMetadata> metadataStore = new HashMap<>();

    // Store metadata associated with a file
    public void storeMetadata(FileMetadata metadata) {
        metadataStore.put(metadata.getFileName(), metadata);
        System.out.println("Metadata stored for: " + metadata.getFileName());
    }

    public FileMetadata getMetadata(String fileName) {
        return metadataStore.get(fileName);
    }

    // Count of all metadata entries stored
    public int getTotalMetadataCount() {
        return metadataStore.size();
    }
}

