package ru.mechaneg.basejava.storage;

import ru.mechaneg.basejava.exception.ExistStorageException;
import ru.mechaneg.basejava.exception.NotExistStorageException;
import ru.mechaneg.basejava.model.Resume;

import java.util.ArrayList;
import java.util.Optional;

public class ListStorage extends AbstractStorage {

    private ArrayList<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public void save(Resume resume) {
        if (resume == null) {
            System.out.println("Unable to save null resume");
            return;
        }
        if (storage.contains(resume)) {
            throw new ExistStorageException(resume.getUuid());
        }

        storage.add(resume);
    }

    @Override
    public Resume get(String uuid) {
        Optional<Resume> result = storage.stream()
                .filter(resume -> resume.getUuid().equals(uuid))
                .findAny();

        if (result.isPresent()) {
            return result.get();
        }

        throw new NotExistStorageException(uuid);
    }

    @Override
    public void delete(String uuid) {
        if (!storage.removeIf(resume -> resume.getUuid().equals(uuid))) {
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    public void update(Resume resume) {
        int index = storage.indexOf(resume);
        if (index < 0) {
            throw new NotExistStorageException(resume.getUuid());
        }

        storage.set(index, resume);
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[storage.size()]);
    }

    @Override
    public int size() {
        return storage.size();
    }
}
