package ru.mechaneg.basejava.storage;

import ru.mechaneg.basejava.exception.ExistStorageException;
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

    /**
     * Finds storage position of corresponding resume with this uuid.
     * If there is no such resume returns negative number.
     */
    protected abstract int findPosition(String uuid);

    protected abstract void deleteAtPosition(int position);

    protected abstract int prepareToInsert(int position);

    @Override
    public void save(Resume resume) {
        if (resume == null) {
            System.out.println("Unable to save null resume");
            return;
        }
        if (curSize == MAX_SIZE) {
            throw new StorageOverflowException(resume.getUuid());
        }

        int insertPos = findPosition(resume.getUuid());

        if (insertPos >= 0) {
            throw new ExistStorageException(resume.getUuid());
        }

        insertPos = prepareToInsert(insertPos);

        storage[insertPos] = resume;
        curSize++;
    }

    @Override
    public void delete(String uuid) {
        int resumePos = findPosition(uuid);
        if (resumePos < 0) {
            throw new NotExistStorageException(uuid);
        }

        deleteAtPosition(resumePos);

        storage[curSize - 1] = null;
        curSize--;
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
    public void update(Resume resume) {
        int resumePos = findPosition(resume.getUuid());
        if (resumePos < 0) {
            throw new NotExistStorageException(resume.getUuid());
        }
        storage[resumePos] = resume;
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
