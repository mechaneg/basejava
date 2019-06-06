package ru.mechaneg.basejava.storage;

import ru.mechaneg.basejava.exception.StorageOverflowException;
import ru.mechaneg.basejava.model.Resume;

import java.util.Arrays;
import java.util.List;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {

    protected static final int MAX_SIZE = 10_000;
    protected Resume[] storage = new Resume[MAX_SIZE];
    protected int curSize = 0;

    protected abstract int prepareToInsert(int position);

    protected abstract void doDeleteAtPosition(int position);

    @Override
    protected Resume getBySearchKey(Integer searchKey) {
        return storage[searchKey];
    }

    @Override
    protected void updateBySearchKey(Integer searchKey, Resume resume) {
        storage[searchKey] = resume;
    }

    @Override
    protected void addNew(Integer searchKey, Resume resume) {
        if (curSize == MAX_SIZE) {
            throw new StorageOverflowException(resume.getUuid());
        }

        int position = prepareToInsert(searchKey);

        storage[position] = resume;
        curSize++;
    }

    @Override
    protected void deleteBySearchKey(Integer searchKey) {
        doDeleteAtPosition(searchKey);
        storage[curSize - 1] = null;
        curSize--;
    }

    @Override
    protected boolean isSearchKeyExist(Integer searchKey) {
        return searchKey >= 0;
    }

    @Override
    public void clear() {
        Arrays.fill(storage, 0, curSize, null);
        curSize = 0;
    }

    @Override
    protected List<Resume> getAll() {
        return Arrays.asList(Arrays.copyOfRange(storage, 0, curSize));
    }

    @Override
    public int size() {
        return curSize;
    }
}
