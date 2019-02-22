package ru.mechaneg.basejava.storage;

import ru.mechaneg.basejava.model.Resume;

public interface IStorage {
    void clear();

    void save(Resume resume);

    Resume get(String uuid);

    void delete(String uuid);

    void update(Resume resume);

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll();

    int size();
}
