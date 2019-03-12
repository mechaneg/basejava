package ru.mechaneg.basejava.storage;

import ru.mechaneg.basejava.exception.StorageOverflowException;
import ru.mechaneg.basejava.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage {

    protected static final int MAX_SIZE = 10_000;
    protected Resume[] storage = new Resume[MAX_SIZE];
    protected int curSize = 0;

    protected abstract int prepareToInsert(int position);

    protected abstract void doDeleteAtPosition(int position);

    @Override
    protected Resume getBySearchKey(Object searchKey) {
        return storage[(int) searchKey];
    }

    @Override
    protected void updateBySearchKey(Object searchKey, Resume resume) {
        storage[(int) searchKey] = resume;
    }

    @Override
    protected void addNewForSearchKey(Object searchKey, Resume resume) {
        if (curSize == MAX_SIZE) {
            throw new StorageOverflowException(resume.getUuid());
        }

        int position = prepareToInsert((int) searchKey);

        storage[position] = resume;
        curSize++;
    }

    @Override
    protected void deleteBySearchKey(Object searchKey) {
        doDeleteAtPosition((int) searchKey);
        storage[curSize - 1] = null;
        curSize--;
    }

    @Override
    protected boolean searchKeyExists(Object searchKey) {
        return (int) searchKey >= 0;
    }

    @Override
    public void clear() {
        Arrays.fill(storage, 0, curSize, null);
        curSize = 0;
    }

    protected Resume[] getAll() {
        return Arrays.copyOf(storage, curSize);
    }

    @Override
    public int size() {
        return curSize;
    }
}
