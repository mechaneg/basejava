package ru.mechaneg.basejava.storage;

import ru.mechaneg.basejava.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage implements IStorage {
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

    /**
     * Complexity depends on findPosition + prepareToInsert realization
     */
    public void save(Resume resume) {
        if (resume == null) {
            System.out.println("Unable to save null resume");
            return;
        }
        if (curSize == MAX_SIZE) {
            System.out.println("Reached max size of array storage");
            return;
        }

        int insertPos = findPosition(resume.getUuid());

        if (insertPos >= 0) {
            System.out.println("Save: resume with uuid '" + resume.getUuid() + "' already exists");
            return;
        }

        insertPos = prepareToInsert(insertPos);

        storage[insertPos] = resume;
        curSize++;
    }

    /**
     * Complexity depends on deleteAtPosition realization
     */
    public void delete(String uuid) {
        int resumePos = findPosition(uuid);
        if (resumePos < 0) {
            System.out.println("Delete: resume with uuid '" + uuid + "' doesn't exist");
            return;
        }

        deleteAtPosition(resumePos);

        storage[curSize - 1] = null;
        curSize--;
    }

    /**
     * Complexity:
     * O(N)
     */
    public void clear() {
        Arrays.fill(storage, 0, curSize, null);
        curSize = 0;
    }

    /**
     * Complexity depends on findPosition realization
     */
    public Resume get(String uuid) {
        int resumePos = findPosition(uuid);
        if (resumePos < 0) {
            System.out.println("Get: resume with uuid '" + uuid + "' doesn't exist");
            return null;
        }
        return storage[resumePos];
    }

    /**
     * Complexity depends on findPosition realization
     */
    public void update(Resume resume) {
        int resumePos = findPosition(resume.getUuid());
        if (resumePos < 0) {
            System.out.println("Update: resume with uuid '" + resume.getUuid() + "' doesn't exist");
            return;
        }
        storage[resumePos] = resume;
    }

    /**
     * Complexity:
     * O(N)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, curSize);
    }

    /**
     * Complexity:
     * O(1)
     */
    public int size() {
        return curSize;
    }
}
