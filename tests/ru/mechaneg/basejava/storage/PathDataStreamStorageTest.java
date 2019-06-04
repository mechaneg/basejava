package ru.mechaneg.basejava.storage;

import ru.mechaneg.basejava.storage.serialization.DataStreamSerializationStrategy;

public class PathDataStreamStorageTest extends AbstractStorageTest {
    public PathDataStreamStorageTest() {
        super(new PathStorage(STORAGE_DIR, new DataStreamSerializationStrategy()));
    }
}
