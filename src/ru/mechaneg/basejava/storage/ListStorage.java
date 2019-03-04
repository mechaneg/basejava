package ru.mechaneg.basejava.storage;

import ru.mechaneg.basejava.exception.NotExistStorageException;
import ru.mechaneg.basejava.model.Resume;

import java.util.ArrayList;
import java.util.Optional;

public class ListStorage extends AbstractStorage {

    private ArrayList<Resume> storage = new ArrayList<>();

    @Override
    protected int findPosition(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void deleteAtPosition(int position) {
        storage.remove(position);
    }

    @Override
    protected void updateElementInStorage(int position, Resume resume) {
        storage.set(position, resume);
    }

    @Override
    protected void addNewElementToStorage(int insertPosition, Resume resume) {
        if (insertPosition >= 0) {
            storage.add(insertPosition, resume);
        } else {
            storage.add(resume);
        }
    }

    @Override
    protected void checkOverflow(String uuidOfResumeToAdd) {
        // do nothing
    }

    @Override
    public void clear() {
        storage.clear();
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
    public Resume[] getAll() {
        return storage.toArray(new Resume[storage.size()]);
    }

    @Override
    public int size() {
        return storage.size();
    }
}
