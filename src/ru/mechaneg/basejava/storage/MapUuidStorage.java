package ru.mechaneg.basejava.storage;

import ru.mechaneg.basejava.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapUuidStorage extends AbstractStorage<String> {

    private Map<String, Resume> storage = new HashMap<>();

    @Override
    protected String findSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected Resume getBySearchKey(String searchKey) {
        return storage.get(searchKey);
    }

    @Override
    protected void deleteBySearchKey(String searchKey) {
        storage.remove(searchKey);
    }

    @Override
    protected void updateBySearchKey(String searchKey, Resume resume) {
        storage.put(searchKey, resume);
    }

    @Override
    protected void addNew(String searchKey, Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected boolean isSearchKeyExist(String searchKey) {
        return storage.containsKey(searchKey);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected List<Resume> getAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public int size() {
        return storage.size();
    }
}
