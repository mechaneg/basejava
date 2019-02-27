package ru.mechaneg.basejava.exception;

public class StorageOverflowException extends StorageException {
    public StorageOverflowException(String uuid) {
        super(uuid);
    }
}
