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
     * Complexity:
     * O(N)
     */
    public void clear() {
        Arrays.fill(storage, 0, curSize, null);
        curSize = 0;
    }

    public abstract void save(Resume resume);

    /**
     * Complexity depends on findPosition realization
     */
    public Resume get(String uuid) {
        int resumePos = findPosition(uuid);
        if (resumePos < 0) {
            System.out.println("Get: resume with uuid '" + uuid + "' doesn't exist");
            return null;
        } else {
            return storage[resumePos];
        }
    }

    public abstract void delete(String uuid);

    /**
     * Complexity depends on findPosition realization
     */
    public void update(Resume resume) {
        int resumePos = findPosition(resume.getUuid());
        if (resumePos < 0) {
            System.out.println("Update: resume with uuid '" + resume.getUuid() + "' doesn't exist");
        } else {
            storage[resumePos] = resume;
        }
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

    /**
     * Finds storage position of corresponding resume with this uuid.
     * If there is no such resume returns negative number.
     */
    protected abstract int findPosition(String uuid);
}
