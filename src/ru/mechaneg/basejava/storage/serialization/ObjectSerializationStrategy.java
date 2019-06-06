package ru.mechaneg.basejava.storage.serialization;

import ru.mechaneg.basejava.exception.StorageException;
import ru.mechaneg.basejava.model.Resume;

import java.io.*;

public class ObjectSerializationStrategy implements ISerializationStrategy {
    @Override
    public void serialize(Resume resume, OutputStream stream) throws IOException {
        try (ObjectOutputStream os = new ObjectOutputStream(stream)) {
            os.writeObject(resume);
        }
    }

    @Override
    public Resume deserialize(InputStream stream) throws IOException {
        try (ObjectInputStream is = new ObjectInputStream(stream)) {
            return (Resume) is.readObject();
        } catch (ClassNotFoundException ex) {
            throw new StorageException("Error reading resume", null, ex);
        }
    }
}
