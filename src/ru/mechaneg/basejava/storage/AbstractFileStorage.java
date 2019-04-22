package ru.mechaneg.basejava.storage;

import ru.mechaneg.basejava.exception.StorageException;
import ru.mechaneg.basejava.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage {
    private File directory;

    AbstractFileStorage(File directory) {
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
        return deserializeResume((File) searchKey);
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
        serializeResume(resume, file);
    }

    @Override
    protected void addNew(Object searchKey, Resume resume) {
        File file = (File) searchKey;
        try {
            file.createNewFile();
        } catch (IOException ex) {
            throw new StorageException("Unable to create a file", resume.getUuid(), ex);
        }
        serializeResume(resume, file);
    }

    @Override
    protected boolean isSearchKeyExist(Object searchKey) {
        return ((File) searchKey).exists();
    }

    @Override
    protected Resume[] getAll() {
        List<Resume> resumes = new ArrayList<>();

        for (File file : directory.listFiles()) {
            resumes.add(deserializeResume(file));
        }

        return resumes.toArray(new Resume[resumes.size()]);
    }

    @Override
    public void clear() {
        for (File file : directory.listFiles()) {
            if (!file.delete()) {
                throw new StorageException("Unable to delete file", file.getName());
            }
        }
    }

    @Override
    public int size() {
        return directory.listFiles().length;
    }

    protected abstract void serializeResume(Resume resume, File file);

    protected abstract Resume deserializeResume(File file);
}
