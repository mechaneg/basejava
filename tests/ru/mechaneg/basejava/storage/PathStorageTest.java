package ru.mechaneg.basejava.storage;

public class PathStorageTest extends AbstractStorageTest {
    public PathStorageTest() {
        super(new PathStorage(STORAGE_DIR, new ObjectSerializationStrategy()));
    }
}
