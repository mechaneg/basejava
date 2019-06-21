package ru.mechaneg.basejava.exception;

public class ExistStorageException extends StorageException {
    public ExistStorageException(String uuid) {
        super(uuid);
    }
    public ExistStorageException(Exception ex) {
        super(ex);
    }
}
