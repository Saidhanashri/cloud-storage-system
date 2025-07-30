package com.cloudstorage.provider;

import com.cloudstorage.manager.CloudStorageManager;

import java.util.Scanner;
import java.io.IOException;
import java.util.Properties;
import java.io.InputStream;

/**
 * Main Application Class for Cloud-of-Clouds Storage System
 * Handles user interface and application lifecycle
 */
public class CloudOfCloudsStorageSystem {

    private static CloudStorageManager storageManager;
    private static Scanner scanner;

    public static void main(String[] args) {
        System.out.println("=== Cloud-of-Clouds Storage System ===");
        System.out.println("Initializing system...");

        try {
            // Load configuration
            loadConfiguration();

            // Initialize components
            storageManager = new CloudStorageManager();
            scanner = new Scanner(System.in);

            // Initialize the storage manager
            storageManager.initialize();

            // Start main application loop
            runApplication();

        } catch (Exception e) {
            System.err.println("Failed to initialize system: " + e.getMessage());
            e.printStackTrace();
        } finally {
            cleanup();
        }
    }

    /**
     * Load application configuration from properties file
     */
    private static void loadConfiguration() throws IOException {
        Properties config = new Properties();
        try (InputStream input = CloudOfCloudsStorageSystem.class
                .getClassLoader().getResourceAsStream("application.properties")) {
            if (input != null) {
                config.load(input);
            }
        }
    }

    /**
     * Main application loop
     */
    private static void runApplication() {
        boolean running = true;

        while (running) {
            try {
                displayMenu();
                int choice = getIntInput("Enter your choice: ");

                switch (choice) {
                    case 1:
                        handleFileUpload();
                        break;
                    case 2:
                        handleFileDownload();
                        break;
                    case 3:
                        handleListFiles();
                        break;
                    case 4:
                        handleShareFile();
                        break;
                    case 5:
                        handleSystemStatus();
                        break;
                    case 6:
                        handleCleanup();
                        break;
                    case 7:
                        running = false;
                        System.out.println("Shutting down system...");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }

                if (running) {
                    System.out.println("\nPress Enter to continue...");
                    scanner.nextLine();
                }

            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    /**
     * Display main menu
     */
    private static void displayMenu() {
        String line = new String(new char[50]).replace('\0', '=');
        System.out.println("\n" + line);
        System.out.println("           CLOUD STORAGE SYSTEM MENU");
        System.out.println(line);
        System.out.println("1. Upload File");
        System.out.println("2. Download File");
        System.out.println("3. List Files");
        System.out.println("4. Share File");
        System.out.println("5. System Status");
        System.out.println("6. Cleanup & Maintenance");
        System.out.println("7. Exit");
        System.out.println(line);
    }

    /**
     * Handle file upload operation
     */
    private static void handleFileUpload() {
        System.out.println("\n--- FILE UPLOAD ---");
        String filePath = getStringInput("Enter file path to upload: ");
        String clientId = getStringInput("Enter client ID: ");

        if (filePath.trim().isEmpty() || clientId.trim().isEmpty()) {
            System.out.println("File path and client ID cannot be empty.");
            return;
        }

        storageManager.uploadFile(filePath, clientId);
    }

    /**
     * Handle file download operation
     */
    private static void handleFileDownload() {
        String fileName = getStringInput("Enter file name to download: ");
        String clientId = getStringInput("Enter client ID: ");

        if (fileName.trim().isEmpty() || clientId.trim().isEmpty()) {
            System.out.println("File name and client ID cannot be empty.");
            return;
        }

        storageManager.downloadFile(fileName, clientId);
    }

    /**
     * Handle list files operation
     */
    private static void handleListFiles() {
        System.out.println("\n--- LIST FILES ---");
        String clientId = getStringInput("Enter client ID: ");

        if (clientId.trim().isEmpty()) {
            System.out.println("Client ID cannot be empty.");
            return;
        }

        storageManager.listFiles(clientId);
    }

    /**
     * Handle file sharing operation
     */
    private static void handleShareFile() {
        System.out.println("\n--- SHARE FILE ---");
        String fileName = getStringInput("Enter file name to share: ");
        String ownerClientId = getStringInput("Enter your client ID: ");
        String targetClientId = getStringInput("Enter target client ID: ");

        if (fileName.trim().isEmpty() || ownerClientId.trim().isEmpty() ||
                targetClientId.trim().isEmpty()) {
            System.out.println("All fields are required.");
            return;
        }

        if (ownerClientId.equals(targetClientId)) {
            System.out.println("Cannot share file with yourself.");
            return;
        }

        storageManager.shareFile(fileName, ownerClientId, targetClientId);
    }

    /**
     * Handle system status display
     */
    private static void handleSystemStatus() {
        System.out.println("\n--- SYSTEM STATUS ---");
        storageManager.displaySystemStatus();
    }

    /**
     * Handle cleanup and maintenance operations
     */
    private static void handleCleanup() {
        System.out.println("\n--- CLEANUP & MAINTENANCE ---");
        System.out.println("1. Clear expired leases");
        System.out.println("2. Cleanup temporary files");
        System.out.println("3. Verify data integrity");
        System.out.println("4. Back to main menu");

        int choice = getIntInput("Enter maintenance option: ");

        switch (choice) {
            case 1:
                storageManager.clearExpiredLeases();
                System.out.println("Expired leases cleared.");
                break;
            case 2:
                storageManager.cleanupTempFiles();
                System.out.println("Temporary files cleaned up.");
                break;
            case 3:
                storageManager.verifyDataIntegrity();
                break;
            case 4:
                return;
            default:
                System.out.println("Invalid option.");
        }
    }

    /**
     * Get string input from user
     */
    private static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    /**
     * Get integer input from user with validation
     */
    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    /**
     * Cleanup resources
     */
    private static void cleanup() {
        if (scanner != null) {
            scanner.close();
        }
        if (storageManager != null) {
            storageManager.shutdown();
        }
        System.out.println("System shutdown complete.");
    }
}