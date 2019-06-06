package ru.mechaneg.basejava.storage;

import ru.mechaneg.basejava.exception.StorageException;
import ru.mechaneg.basejava.model.Resume;
import ru.mechaneg.basejava.storage.serialization.ISerializationStrategy;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage {
    private Path directory;
    private ISerializationStrategy serializationStrategy;

    PathStorage(String directoryName, ISerializationStrategy serializationStrategy) {
        this.directory = Paths.get(directoryName);
        if (!Files.isDirectory(directory)) {
            throw new IllegalArgumentException(directory + " is not a directory");
        }
        this.serializationStrategy = serializationStrategy;
    }

    @Override
    protected Object findSearchKey(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    protected Resume getBySearchKey(Object searchKey) {
        try {
            return serializationStrategy.deserialize(createInputStream((Path) searchKey));
        } catch (IOException ex) {
            throw new StorageException("Path read error", ((Path)searchKey).toString(), ex);
        }

    }

    @Override
    protected void deleteBySearchKey(Object searchKey) {
        try {
            Files.deleteIfExists((Path) searchKey);
        } catch (IOException ex) {
            throw new StorageException("Unable to delete file", searchKey.toString(), ex);
        }
    }

    @Override
    protected void updateBySearchKey(Object searchKey, Resume resume) {
        Path file = (Path) searchKey;
        try {
            serializationStrategy.serialize(resume, createOutputStream(file));
        } catch (IOException ex) {
            throw new StorageException("File read error", file.toString(), ex);
        }
    }

    @Override
    protected void addNew(Object searchKey, Resume resume) {
        try {
            Path file = Files.createFile((Path) searchKey);
            updateBySearchKey(file, resume);
        } catch (IOException ex) {
            throw new StorageException("Unable to create file", searchKey.toString(), ex);
        }
    }

    @Override
    protected boolean isSearchKeyExist(Object searchKey) {
        return Files.isRegularFile((Path) searchKey);
    }

    @Override
    protected List<Resume> getAll() {
        return getDirectoryFilesStream().map(this::getBySearchKey).collect(Collectors.toList());
    }

    @Override
    public void clear() {
        getDirectoryFilesStream().forEach(this::deleteBySearchKey);
    }

    @Override
    public int size() {
        return (int) getDirectoryFilesStream().count();
    }

    private OutputStream createOutputStream(Path file) {
        try {
            return Files.newOutputStream(file);
        } catch (IOException ex) {
            throw new StorageException("Failed to create output stream", file.toString(), ex);
        }
    }

    private InputStream createInputStream(Path file) {
        try {
            return Files.newInputStream(file);
        } catch (IOException ex) {
            throw new StorageException("Failed to create input stream", file.toString(), ex);
        }
    }

    private Stream<Path> getDirectoryFilesStream() {
        try {
            return Files.list(directory);
        } catch (IOException ex) {
            throw new StorageException("Error opening directory", directory.toString(), ex);
        }
    }
}
