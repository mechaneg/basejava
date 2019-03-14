package ru.mechaneg.basejava.storage;

import ru.mechaneg.basejava.model.Resume;

import java.util.Comparator;
import java.util.NavigableSet;
import java.util.TreeSet;

public class SetStorage extends AbstractStorage {

    private NavigableSet<Resume> storage = new TreeSet<>(new Comparator<Resume>() {
        @Override
        public int compare(Resume lhs, Resume rhs) {
            return lhs.getUuid().compareTo(rhs.getUuid());
        }
    });

    @Override
    protected Resume findSearchKey(String uuid) {
        Resume searchKey = storage.ceiling(new Resume(uuid, "stub name"));
        if (searchKey == null || !searchKey.getUuid().equals(uuid)) {
            return null;
        }
        return searchKey;
    }

    @Override
    protected Resume getBySearchKey(Object searchKey) {
        return (Resume) searchKey;
    }

    @Override
    protected void deleteBySearchKey(Object searchKey) {
        storage.remove(searchKey);
    }

    @Override
    protected void updateBySearchKey(Object searchKey, Resume resume) {
        storage.remove(searchKey);
        storage.add(resume);
    }

    @Override
    protected void addNew(Object searchKey, Resume resume) {
        storage.add(resume);
    }

    @Override
    protected boolean isSearchKeyExist(Object searchKey) {
        return searchKey != null;
    }

    @Override
    protected Resume[] getAll() {
        return storage.toArray(new Resume[storage.size()]);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public int size() {
        return storage.size();
    }
}
