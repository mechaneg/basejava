package ru.mechaneg.basejava.storage;

import ru.mechaneg.basejava.storage.serialization.XmlSerializationStrategy;

public class PathXmlStorageTest  extends AbstractStorageTest {
    public PathXmlStorageTest() { super(new PathStorage(STORAGE_DIR, new XmlSerializationStrategy())); }
}
