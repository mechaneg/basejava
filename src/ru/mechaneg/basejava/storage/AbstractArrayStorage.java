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
    protected Resume getAtPosition(int position) {
        return storage[position];
    }

    @Override
    protected void updateAtPosition(int position, Resume resume) {
        storage[position] = resume;
    }

    @Override
    protected void addNewAtPosition(int position, Resume resume) {
        if (curSize == MAX_SIZE) {
            throw new StorageOverflowException(resume.getUuid());
        }

        position = prepareToInsert(position);

        storage[position] = resume;
        curSize++;
    }

    @Override
    protected void deleteAtPosition(int position) {
        doDeleteAtPosition(position);
        storage[curSize - 1] = null;
        curSize--;
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
