package com.cloudstorage.model;

import java.time.LocalDateTime;
import java.util.List;

public class FileMetadata {
    private String fileName;
    private String clientId;
    private long fileSize;
    private String encryptionKey;
    private List<String> chunkIds;
    private LocalDateTime uploadTime;

    public FileMetadata(String fileName, String clientId, long fileSize, String encryptionKey,
                        List<String> chunkIds, LocalDateTime uploadTime) {
        this.fileName = fileName;
        this.clientId = clientId;
        this.fileSize = fileSize;
        this.encryptionKey = encryptionKey;
        this.chunkIds = chunkIds;
        this.uploadTime = uploadTime;
    }

    public String getFileName() {
        return fileName;
    }

    public String getClientId() {
        return clientId;
    }

    public long getFileSize() {
        return fileSize;
    }

    public String getEncryptionKey() {
        return encryptionKey;
    }

    public List<String> getChunkIds() {
        return chunkIds;
    }

    public LocalDateTime getUploadTime() {
        return uploadTime;
    }
}

