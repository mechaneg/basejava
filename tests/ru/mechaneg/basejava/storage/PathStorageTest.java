package ru.mechaneg.basejava.storage;

import ru.mechaneg.basejava.storage.serialization.ObjectSerializationStrategy;

public class PathStorageTest extends AbstractStorageTest {
    public PathStorageTest() {
        super(new PathStorage(STORAGE_DIR, new ObjectSerializationStrategy()));
    }
}
