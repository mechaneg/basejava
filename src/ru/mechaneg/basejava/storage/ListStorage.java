package ru.mechaneg.basejava.storage;

import ru.mechaneg.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {

    private List<Resume> storage = new ArrayList<>();

    @Override
    protected Object findSearchKey(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected Resume getBySearchKey(Object searchKey) {
        return storage.get((int) searchKey);
    }

    @Override
    protected void deleteBySearchKey(Object searchKey) {
        storage.remove((int) searchKey);
    }

    @Override
    protected void updateBySearchKey(Object searchKey, Resume resume) {
        storage.set((int) searchKey, resume);
    }

    @Override
    protected void addNewForSearchKey(Object searchKey, Resume resume) {
        storage.add(resume);
    }

    @Override
    protected boolean searchKeyExists(Object searchKey) {
        return (int) searchKey >= 0;
    }

    @Override
    public void clear() {
        storage.clear();
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
