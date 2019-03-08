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
    protected Resume getAtId(Object id) {
        return storage[(int) id];
    }

    @Override
    protected void updateAtId(Object id, Resume resume) {
        storage[(int) id] = resume;
    }

    @Override
    protected void addNewAtId(Object id, Resume resume) {
        if (curSize == MAX_SIZE) {
            throw new StorageOverflowException(resume.getUuid());
        }

        int position = prepareToInsert((int) id);

        storage[position] = resume;
        curSize++;
    }

    @Override
    protected void deleteAtId(Object id) {
        doDeleteAtPosition((int) id);
        storage[curSize - 1] = null;
        curSize--;
    }

    @Override
    protected boolean idExists(Object id) {
        return (int) id >= 0;
    }

    @Override
    public void clear() {
        Arrays.fill(storage, 0, curSize, null);
        curSize = 0;
    }

    @Override
    public Resume[] getAll() {
        return Arrays.copyOf(storage, curSize);
    }

    @Override
    public int size() {
        return curSize;
    }
}
