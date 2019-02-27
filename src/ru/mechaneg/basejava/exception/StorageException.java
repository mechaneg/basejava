package ru.mechaneg.basejava.exception;

public class StorageException extends RuntimeException {

    private final String uuid;

    public String getUuid() {
        return uuid;
    }

    public StorageException(String uuid) {
        this.uuid = uuid;
    }
}
