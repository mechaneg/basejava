package ru.mechaneg.basejava.storage;

import java.io.File;

public class FileStorageTest extends AbstractStorageTest {
    public FileStorageTest() {
        super(new FileStorage(new File(STORAGE_DIR), new ObjectSerializationStrategy()));
    }
}