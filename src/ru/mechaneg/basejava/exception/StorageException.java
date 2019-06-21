package ru.mechaneg.basejava.exception;

public class StorageException extends RuntimeException {

    private String uuid;

    public StorageException(String uuid) {
        this.uuid = uuid;
    }

    public StorageException(String message, String uuid) {
        super(message);
        this.uuid = uuid;
    }

    public StorageException(String message, String uuid, Exception ex) {
        super(message, ex);
        this.uuid = uuid;
    }

    public StorageException(String message, Exception ex) {
        super(message, ex);
    }

    public StorageException(Exception ex) {
        super(ex);
    }

    public String getUuid() {
        return uuid;
    }
}
