package ru.mechaneg.basejava.storage;

import ru.mechaneg.basejava.storage.serialization.ObjectSerializationStrategy;

public class PathObjectStreamStorageTest extends AbstractStorageTest {
    public PathObjectStreamStorageTest() {
        super(new PathStorage(STORAGE_DIR, new ObjectSerializationStrategy()));
    }
}
