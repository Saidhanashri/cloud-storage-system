package com.cloudstorage.provider;

import com.cloudstorage.model.FileChunk;

public class CloudProvider {
    private String name;
    private String bucket;
    private boolean available;

    public CloudProvider(String name, String bucket, boolean available) {
        this.name = name;
        this.bucket = bucket;
        this.available = available;
    }

    public String getName() {
        return name;
    }

    public boolean isAvailable() {
        return available;
    }

    public void storeChunk(FileChunk chunk) {
        // simulate storing chunk
        System.out.println("Storing chunk in " + name + ": " + chunk.getChunkId());
    }

    public FileChunk retrieveChunk(String chunkId) {
        // simulate retrieval
        return new FileChunk(chunkId, new byte[0]); // stubbed
    }
}

