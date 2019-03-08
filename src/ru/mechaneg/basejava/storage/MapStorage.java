package ru.mechaneg.basejava.storage;

import ru.mechaneg.basejava.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {

    private Map<String, Resume> storage = new HashMap<>();

    @Override
    protected Object findId(String uuid) {
        return storage.containsKey(uuid) ? uuid : null;
    }

    @Override
    protected Resume getAtId(Object id) {
        return storage.get(id);
    }

    @Override
    protected void deleteAtId(Object id) {
        storage.remove(id);
    }

    @Override
    protected void updateAtId(Object id, Resume resume) {
        storage.put((String) id, resume);
    }

    @Override
    protected void addNewAtId(Object id, Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected boolean idExists(Object id) {
        return id != null;
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        return storage.values().toArray(new Resume[storage.size()]);
    }

    @Override
    public int size() {
        return storage.size();
    }
}
