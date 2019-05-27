package ru.mechaneg.basejava.storage;

import ru.mechaneg.basejava.model.Resume;
import ru.mechaneg.basejava.storage.serialization.ISerializationStrategy;

import java.io.InputStream;
import java.io.OutputStream;

public abstract class AbstractSerializableStorage extends AbstractStorage {
    private ISerializationStrategy serializationStrategy;

    AbstractSerializableStorage(ISerializationStrategy strategy) {
        this.serializationStrategy = strategy;
    }

    protected void serialize(Resume resume, OutputStream stream) {
        serializationStrategy.serialize(resume, stream);
    }

    protected Resume deserialize(InputStream stream) {
        return serializationStrategy.deserialize(stream);
    }
}
