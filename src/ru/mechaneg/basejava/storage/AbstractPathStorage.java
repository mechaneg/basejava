package ru.mechaneg.basejava.storage;

import ru.mechaneg.basejava.exception.StorageException;
import ru.mechaneg.basejava.model.Resume;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractPathStorage extends AbstractStorage {
    private Path directory;

    AbstractPathStorage(String directoryName) {
        directory = Paths.get(directoryName);
        if (!Files.isDirectory(directory) || Files.isWritable(directory)) {
            throw new IllegalArgumentException(directory + " is not a directory or is not writable");
        }
    }

    @Override
    protected Object findSearchKey(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    protected Resume getBySearchKey(Object searchKey) {
        return deserialize((Path)searchKey);
    }

    @Override
    protected void deleteBySearchKey(Object searchKey) {
        try {
            Files.deleteIfExists((Path)searchKey);
        } catch (IOException ex) {
            throw new StorageException("Unable to delete file", searchKey.toString(), ex);
        }
    }

    @Override
    protected void updateBySearchKey(Object searchKey, Resume resume) {
        serialize(resume, (Path)searchKey);
    }

    @Override
    protected void addNew(Object searchKey, Resume resume) {

        try {
            Path file = Files.createFile((Path)searchKey);
            serialize(resume, file);
        } catch (IOException ex) {
            throw new StorageException("Unable to create file", searchKey.toString(), ex);
        }
    }

    @Override
    protected boolean isSearchKeyExist(Object searchKey) {
        return Files.exists((Path)searchKey);
    }

    @Override
    protected List<Resume> getAll() {
        List<Resume> resumes = new ArrayList<>();

        try (DirectoryStream<Path> dirStream = Files.newDirectoryStream(directory)) {
            for (Path file : dirStream) {
                resumes.add(deserialize(file));
            }
        } catch (IOException ex) {
            throw new StorageException("Failed to list files in directory", directory.toString(), ex);
        }

        return resumes;
    }

    abstract void serialize(Resume resume, Path file);

    abstract Resume deserialize(Path file);
}
