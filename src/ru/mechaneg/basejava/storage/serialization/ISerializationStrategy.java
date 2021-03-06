package ru.mechaneg.basejava.storage.serialization;

import ru.mechaneg.basejava.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface ISerializationStrategy {
    void serialize(Resume resume, OutputStream stream) throws IOException;

    Resume deserialize(InputStream stream) throws IOException;
}
