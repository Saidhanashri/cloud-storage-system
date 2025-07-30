package com.cloudstorage.manager;

import com.cloudstorage.model.FileChunk;
import com.cloudstorage.model.FileMetadata;
import com.cloudstorage.provider.CloudProvider;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.*;

public class CloudStorageManager {

    private final List<CloudProvider> cloudProviders;
    private final MetadataManager metadataManager;
    private final EncryptionManager encryptionManager;
    private final LeaseManager leaseManager;
    private final DatabaseManager databaseManager;

    public CloudStorageManager() {
        this.cloudProviders = new ArrayList<>();
        this.metadataManager = new MetadataManager();
        this.encryptionManager = new EncryptionManager();
        this.leaseManager = new LeaseManager();
        this.databaseManager = new DatabaseManager();
    }

    public void initialize() {
        cloudProviders.add(new CloudProvider("AWS", "aws-bucket-1", true));
        cloudProviders.add(new CloudProvider("Azure", "azure-container-1", true));
        cloudProviders.add(new CloudProvider("GCP", "gcp-bucket-1", true));

        databaseManager.initializeDatabase();
        createDirectories();
        System.out.println("System initialized with " + cloudProviders.size() + " cloud providers");
    }

    private void createDirectories() {
        try {
            Files.createDirectories(Paths.get("uploads"));
            Files.createDirectories(Paths.get("downloads"));
            Files.createDirectories(Paths.get("logs"));
        } catch (IOException e) {
            System.err.println("Failed to create directories: " + e.getMessage());
        }
    }

    public void uploadFile(String filePath, String clientId) {
        try {
            File file = new File(filePath);
            if (!file.exists() || file.length() == 0) {
                System.out.println("Invalid file: " + filePath);
                return;
            }

            String leaseId = leaseManager.acquireLease(file.getName(), clientId);
            if (leaseId == null) {
                System.out.println("Cannot acquire lease for file: " + file.getName());
                return;
            }

            byte[] fileData = Files.readAllBytes(file.toPath());
            String encryptionKey = encryptionManager.generateKey();
            byte[] encryptedData = encryptionManager.encrypt(fileData, encryptionKey);

            List<FileChunk> chunks = splitIntoChunks(encryptedData, file.getName());

            List<String> chunkIds = new ArrayList<>();
            for (FileChunk chunk : chunks) {
                String chunkId = storeChunkInClouds(chunk);
                if (chunkId != null) {
                    chunkIds.add(chunkId);
                }
            }

            FileMetadata metadata = new FileMetadata(
                    file.getName(),
                    clientId,
                    file.length(),
                    encryptionKey,
                    chunkIds,
                    LocalDateTime.now()
            );

            metadataManager.storeMetadata(metadata);
            databaseManager.saveFileRecord(metadata);
            leaseManager.releaseLease(leaseId);

            System.out.println("File uploaded successfully!");

        } catch (Exception e) {
            System.err.println("Upload failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void downloadFile(String fileName, String clientId) {
        try {
            if (!databaseManager.hasAccess(fileName, clientId)) {
                System.out.println("Access denied.");
                return;
            }

            FileMetadata metadata = metadataManager.getMetadata(fileName);
            if (metadata == null) {
                System.out.println("Metadata not found.");
                return;
            }

            List<FileChunk> chunks = new ArrayList<>();
            for (String chunkId : metadata.getChunkIds()) {
                FileChunk chunk = retrieveChunkFromClouds(chunkId);
                if (chunk != null) {
                    chunks.add(chunk);
                }
            }

            byte[] encryptedData = reconstructFromChunks(chunks);
            byte[] decryptedData = encryptionManager.decrypt(encryptedData, metadata.getEncryptionKey());
            Files.write(Paths.get("downloads/" + fileName), decryptedData);

            System.out.println("File downloaded successfully!");

        } catch (Exception e) {
            System.err.println("Download error: " + e.getMessage());
        }
    }

    public void listFiles(String clientId) {
        try {
            List<FileMetadata> personal = databaseManager.getFilesByClient(clientId);
            List<FileMetadata> shared = databaseManager.getSharedFiles(clientId);

            System.out.println("\n=== Your Files ===");
            if (personal.isEmpty()) System.out.println("No files found.");
            else personal.forEach(f ->
                    System.out.println("- " + f.getFileName() + " (" + formatFileSize(f.getFileSize()) + ") - " + f.getUploadTime())
            );

            System.out.println("\n=== Shared with You ===");
            if (shared.isEmpty()) System.out.println("No shared files found.");
            else shared.forEach(f ->
                    System.out.println("- " + f.getFileName() + " (shared by " + f.getClientId() + ")")
            );
        } catch (Exception e) {
            System.err.println("List error: " + e.getMessage());
        }
    }

    public void shareFile(String fileName, String ownerClientId, String targetClientId) {
        try {
            if (!databaseManager.hasAccess(fileName, ownerClientId)) {
                System.out.println("You do not own this file.");
                return;
            }

            databaseManager.shareFile(fileName, ownerClientId, targetClientId);
            System.out.println("File shared with " + targetClientId);
        } catch (Exception e) {
            System.err.println("Share error: " + e.getMessage());
        }
    }

    public void displaySystemStatus() {
        System.out.println("=== System Status ===");
        cloudProviders.forEach(p ->
                System.out.println("- " + p.getName() + ": " + (p.isAvailable() ? "Available" : "Unavailable"))
        );
        System.out.println("Active leases: " + leaseManager.getActiveLeaseCount());
        System.out.println("Total files: " + databaseManager.getTotalFileCount());
        System.out.println("Metadata entries: " + metadataManager.getTotalMetadataCount());
    }

    public void clearExpiredLeases() {
        leaseManager.clearExpiredLeases();
    }

    public void cleanupTempFiles() {
        leaseManager.cleanupTempFiles();
    }

    public void verifyDataIntegrity() {
        leaseManager.verifyDataIntegrity();
    }

    public void shutdown() {
        System.out.println("System shut down.");
    }

    // Helper Methods

    private List<FileChunk> splitIntoChunks(byte[] data, String fileName) {
        List<FileChunk> chunks = new ArrayList<>();
        int chunkSize = 1024 * 1024;
        for (int i = 0; i < data.length; i += chunkSize) {
            int end = Math.min(i + chunkSize, data.length);
            byte[] chunkData = Arrays.copyOfRange(data, i, end);
            chunks.add(new FileChunk(fileName + "_chunk_" + (i / chunkSize), chunkData));
        }
        return chunks;
    }

    private String storeChunkInClouds(FileChunk chunk) {
        int replication = 2, count = 0;
        for (CloudProvider provider : cloudProviders) {
            if (provider.isAvailable() && count < replication) {
                try {
                    provider.storeChunk(chunk);
                    count++;
                } catch (Exception e) {
                    System.err.println("Failed in " + provider.getName() + ": " + e.getMessage());
                }
            }
        }
        return count > 0 ? chunk.getChunkId() : null;
    }

    private FileChunk retrieveChunkFromClouds(String chunkId) {
        for (CloudProvider provider : cloudProviders) {
            if (provider.isAvailable()) {
                try {
                    FileChunk chunk = provider.retrieveChunk(chunkId);
                    if (chunk != null) return chunk;
                } catch (Exception e) {
                    System.err.println("Retrieve error from " + provider.getName());
                }
            }
        }
        return null;
    }

    private byte[] reconstructFromChunks(List<FileChunk> chunks) throws IOException {
        chunks.sort(Comparator.comparing(FileChunk::getChunkId));
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        for (FileChunk chunk : chunks) output.write(chunk.getData());
        return output.toByteArray();
    }

    private String formatFileSize(long size) {
        if (size < 1024) return size + " B";
        else if (size < 1024 * 1024) return String.format("%.1f KB", size / 1024.0);
        else if (size < 1024 * 1024 * 1024) return String.format("%.1f MB", size / (1024.0 * 1024.0));
        else return String.format("%.1f GB", size / (1024.0 * 1024.0 * 1024.0));
    }
}

