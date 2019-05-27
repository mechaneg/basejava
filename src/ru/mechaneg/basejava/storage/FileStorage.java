package ru.mechaneg.basejava.storage;

import ru.mechaneg.basejava.exception.StorageException;
import ru.mechaneg.basejava.model.Resume;
import ru.mechaneg.basejava.storage.serialization.ISerializationStrategy;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileStorage extends AbstractSerializableStorage {
    private File directory;

    FileStorage(File directory, ISerializationStrategy serializationStrategy) {
        super(serializationStrategy);

        Objects.requireNonNull(directory);
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not a directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is forbidden for read/write");
        }
        this.directory = directory;
    }

    @Override
    protected Object findSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected Resume getBySearchKey(Object searchKey) {
        return deserialize(createInputStream((File) searchKey));
    }

    @Override
    protected void deleteBySearchKey(Object searchKey) {
        File file = (File) searchKey;
        if (!file.delete()) {
            throw new StorageException("Unable to delete file", file.getName());
        }
    }

    @Override
    protected void updateBySearchKey(Object searchKey, Resume resume) {
        File file = (File) searchKey;
        serialize(resume, createOutputStream(file));
    }

    @Override
    protected void addNew(Object searchKey, Resume resume) {
        File file = (File) searchKey;
        try {
            if (!file.createNewFile()) {
                throw new IOException("File already exists");
            }
        } catch (IOException ex) {
            throw new StorageException("Unable to create a file", resume.getUuid(), ex);
        }
        serialize(resume, createOutputStream(file));
    }

    @Override
    protected boolean isSearchKeyExist(Object searchKey) {
        return ((File) searchKey).exists();
    }

    @Override
    protected List<Resume> getAll() {
        List<Resume> resumes = new ArrayList<>();

        for (File file : checkForNull(directory.listFiles())) {
            resumes.add(deserialize(createInputStream(file)));
        }

        return resumes;
    }

    @Override
    public void clear() {
        for (File file : checkForNull(directory.listFiles())) {
            if (!file.delete()) {
                throw new StorageException("Unable to delete file", file.getName());
            }
        }
    }

    @Override
    public int size() {
        return checkForNull(directory.listFiles()).length;
    }

    private File[] checkForNull(File[] files) {
        if (files == null) {
            throw new StorageException("Working directory is not a directory or IO error happened");
        }
        return files;
    }

    private OutputStream createOutputStream(File file) {
        try {
            return new BufferedOutputStream(new FileOutputStream(file));
        } catch (FileNotFoundException ex) {
            throw new StorageException("Unable to find file", file.getName());
        }
    }

    private InputStream createInputStream(File file) {
        try {
            return new BufferedInputStream(new FileInputStream(file));
        } catch (FileNotFoundException ex) {
            throw new StorageException("Unable to find file", file.getName());
        }
    }
}
