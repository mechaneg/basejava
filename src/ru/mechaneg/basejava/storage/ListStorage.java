package ru.mechaneg.basejava.storage;

import ru.mechaneg.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {

    private List<Resume> storage = new ArrayList<>();

    @Override
    protected Integer findSearchKey(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected Resume getBySearchKey(Integer searchKey) {
        return storage.get(searchKey);
    }

    @Override
    protected void deleteBySearchKey(Integer searchKey) {
        storage.remove((int) searchKey);
    }

    @Override
    protected void updateBySearchKey(Integer searchKey, Resume resume) {
        storage.set(searchKey, resume);
    }

    @Override
    protected void addNew(Integer searchKey, Resume resume) {
        storage.add(resume);
    }

    @Override
    protected boolean isSearchKeyExist(Integer searchKey) {
        return searchKey >= 0;
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected List<Resume> getAll() {
        return storage;
    }

    @Override
    public int size() {
        return storage.size();
    }
}
