package ru.mechaneg.basejava.storage;

import ru.mechaneg.basejava.exception.StorageException;
import ru.mechaneg.basejava.model.Resume;

import java.io.*;

public class ObjectSerializationStrategy implements ISerializationStrategy {
    @Override
    public void serialize(Resume resume, OutputStream stream) {
        try (ObjectOutputStream os = new ObjectOutputStream(stream)) {
            os.writeObject(resume);
        } catch (IOException ex) {
            throw new StorageException("Unable to create object out stream", resume.getUuid(), ex);
        }
    }

    @Override
    public Resume deserialize(InputStream stream) {
        try (ObjectInputStream is = new ObjectInputStream(stream)) {
            return (Resume)is.readObject();
        } catch (IOException ex) {
            throw new StorageException("Unable to create object in stream", null, ex);
        } catch (ClassNotFoundException ex) {
            throw new StorageException("Error reading resume", null, ex);
        }
    }
}
