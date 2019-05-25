package ru.mechaneg.basejava.storage;

import ru.mechaneg.basejava.model.Resume;
import java.io.InputStream;
import java.io.OutputStream;

public class PathStorage extends AbstractPathStorage {
    private ISerializationStrategy serializationStrategy;

    PathStorage(String directory, ISerializationStrategy serializationStrategy) {
        super(directory);
        this.serializationStrategy = serializationStrategy;
    }

    @Override
    void serialize(Resume resume, OutputStream stream) {
        serializationStrategy.serialize(resume, stream);
    }

    @Override
    Resume deserialize(InputStream stream) {
        return serializationStrategy.deserialize(stream);
    }
}
