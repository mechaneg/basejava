package ru.mechaneg.basejava.exception;

public class NotExistStorageException extends StorageException {
    public NotExistStorageException(String uuid) {
        super(uuid);
    }
}
