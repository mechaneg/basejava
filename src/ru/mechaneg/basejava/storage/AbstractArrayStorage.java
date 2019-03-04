package ru.mechaneg.basejava.storage;

import ru.mechaneg.basejava.exception.NotExistStorageException;
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

    @Override
    protected void updateElementInStorage(int position, Resume resume) {
        storage[position] = resume;
    }

    @Override
    protected void addNewElementToStorage(int insertPosition, Resume resume) {
        insertPosition = prepareToInsert(insertPosition);

        storage[insertPosition] = resume;
        curSize++;
    }

    @Override
    protected void checkOverflow(String uuidOfResumeToAdd) {
        if (curSize == MAX_SIZE) {
            throw new StorageOverflowException(uuidOfResumeToAdd);
        }
    }

    @Override
    public void clear() {
        Arrays.fill(storage, 0, curSize, null);
        curSize = 0;
    }

    @Override
    public Resume get(String uuid) {
        int resumePos = findPosition(uuid);
        if (resumePos < 0) {
            throw new NotExistStorageException(uuid);
        }
        return storage[resumePos];
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
