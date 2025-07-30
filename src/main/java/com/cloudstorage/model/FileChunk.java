package com.cloudstorage.model;

public class FileChunk {
    private String chunkId;
    private byte[] data;

    public FileChunk(String chunkId, byte[] data) {
        this.chunkId = chunkId;
        this.data = data;
    }

    public String getChunkId() {
        return chunkId;
    }

    public byte[] getData() {
        return data;
    }
}
