package ru.mechaneg.basejava.storage;

import ru.mechaneg.basejava.storage.serialization.JsonSerializatonStrategy;

public class PathJsonStorageTest extends AbstractStorageTest {
    public PathJsonStorageTest() { super(new PathStorage(STORAGE_DIR, new JsonSerializatonStrategy())); }
}
