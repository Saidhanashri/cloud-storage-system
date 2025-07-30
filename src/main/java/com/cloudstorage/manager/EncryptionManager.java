package com.cloudstorage.manager;

public class EncryptionManager {
    public String generateKey() {
        return "sample_key";
    }

    public byte[] encrypt(byte[] data, String key) {
        return data; // placeholder
    }

    public byte[] decrypt(byte[] data, String key) {
        return data; // placeholder
    }
}
