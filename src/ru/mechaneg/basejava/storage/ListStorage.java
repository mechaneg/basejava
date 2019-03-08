package ru.mechaneg.basejava.storage;

import ru.mechaneg.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {

    private List<Resume> storage = new ArrayList<>();

    @Override
    protected Object findId(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected Resume getAtId(Object id) {
        return storage.get((int) id);
    }

    @Override
    protected void deleteAtId(Object id) {
        storage.remove((int) id);
    }

    @Override
    protected void updateAtId(Object id, Resume resume) {
        storage.set((int) id, resume);
    }

    @Override
    protected void addNewAtId(Object id, Resume resume) {
        storage.add(resume);
    }

    @Override
    protected boolean idExists(Object id) {
        return (int) id >= 0;
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
