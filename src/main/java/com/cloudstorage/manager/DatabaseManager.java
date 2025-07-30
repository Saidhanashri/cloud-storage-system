package com.cloudstorage.manager;

import com.cloudstorage.model.FileMetadata;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class DatabaseManager {

    // In-memory storage simulation
    private final Map<String, List<FileMetadata>> userFiles = new ConcurrentHashMap<>();
    private final Map<String, Set<String>> sharedFiles = new ConcurrentHashMap<>();
    private final List<FileMetadata> allFiles = Collections.synchronizedList(new ArrayList<>());

    // Initialize in-memory DB
    public void initializeDatabase() {
        System.out.println("In-memory database initialized.");
    }

    // Save uploaded file metadata
    public void saveFileRecord(FileMetadata metadata) {
        allFiles.add(metadata);
        userFiles.computeIfAbsent(metadata.getClientId(), k -> new ArrayList<>()).add(metadata);
        System.out.println("✅ Saved file for user '" + metadata.getClientId() + "'");
        System.out.println("Saved file count for " + metadata.getClientId() + ": " + userFiles.get(metadata.getClientId()).size());
    }

    // Check if the user has access to the file (ownership or shared)
    public boolean hasAccess(String fileName, String clientId) {
        List<FileMetadata> owned = userFiles.getOrDefault(clientId, Collections.emptyList());
        if (owned.stream().anyMatch(meta -> meta.getFileName().equals(fileName))) {
            return true;
        }

        Set<String> shared = sharedFiles.getOrDefault(clientId, Collections.emptySet());
        return shared.contains(fileName);
    }

    // Get files owned by the client
    public List<FileMetadata> getFilesByClient(String clientId) {
        return userFiles.getOrDefault(clientId, Collections.emptyList())
                .stream()
                .filter(meta -> meta.getClientId().equals(clientId))
                .collect(Collectors.toList());
    }

    // Get files shared with the client
    public List<FileMetadata> getSharedFiles(String clientId) {
        Set<String> sharedFileNames = sharedFiles.getOrDefault(clientId, Collections.emptySet());
        return allFiles.stream()
                .filter(meta -> sharedFileNames.contains(meta.getFileName()))
                .collect(Collectors.toList());
    }

    // Share a file from owner to target client
    public void shareFile(String fileName, String owner, String target) {
        List<FileMetadata> owned = userFiles.getOrDefault(owner, Collections.emptyList());
        boolean ownsFile = owned.stream().anyMatch(meta -> meta.getFileName().equals(fileName));

        if (ownsFile) {
            sharedFiles.computeIfAbsent(target, k -> new HashSet<>()).add(fileName);
            System.out.println("File '" + fileName + "' shared with " + target);
        } else {
            System.out.println("Share failed: Owner does not have the file.");
        }
    }

    // Get total file count in the system
    public int getTotalFileCount() {
        return allFiles.size();
    }
}
