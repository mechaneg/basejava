package ru.mechaneg.basejava.storage.serialization;

import ru.mechaneg.basejava.exception.StorageException;
import ru.mechaneg.basejava.model.Resume;

import java.io.*;

public class DataStreamSerializationStrategy implements ISerializationStrategy {
    @Override
    public void serialize(Resume resume, OutputStream stream) {
        try (DataOutputStream dos = new DataOutputStream(stream)) {

            resume.write(dos);

        } catch (IOException ex) {
            throw new StorageException("Unable to create data output stream", resume.getUuid(), ex);
        }
    }

    @Override
    public Resume deserialize(InputStream stream) {
        try (DataInputStream dis = new DataInputStream(stream)) {
            Resume resumeFactory = new Resume();
            return resumeFactory.read(dis);
        } catch (IOException ex) {
            throw new StorageException("Unable to create data input stream", null, ex);
        }
    }
}
