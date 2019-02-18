import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {

    private static final int MAX_SIZE = 10000;
    private Resume[] storage = new Resume[MAX_SIZE];
    private int curSize = 0;

    private boolean isInStorage(String uuid) {
        return Arrays.stream(storage, 0, curSize).anyMatch(
            element -> element.uuid.equals(uuid)
        );
    }

    public void clear() {
        Arrays.fill(storage, 0, curSize, null);
        curSize = 0;
    }

    public void save(Resume resume) {
        if (resume == null) {
            throw new IllegalArgumentException("resume is null");
        }
        if (isInStorage(resume.uuid)) {
            throw new DuplicateElementException();
        }
        if (curSize == MAX_SIZE) {
            throw new StorageOverflowException();
        }

        storage[curSize] = resume;
        curSize++;
    }

    public Resume get(String uuid) {
        for (int i = 0; i < curSize; i++) {
            if (storage[i].uuid.equals(uuid)) {
                return storage[i];
            }
        }
        throw new AbsentElementException();
    }

    public void delete(String uuid) {
        for (int i = 0; i < curSize; i++) {
            if (storage[i].uuid.equals(uuid)) {
                storage[i] = storage[curSize - 1];
                storage[curSize - 1] = null;
                curSize--;
                return;
            }
        }
        throw new AbsentElementException();
    }

    public void update(Resume resume) {
        for (int i = 0; i < curSize; i++) {
            if (storage[i].uuid.equals(resume.uuid)) {
                storage[i] = resume;
                return;
            }
        }
        throw new AbsentElementException();
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, curSize);
    }

    public int size() {
        return curSize;
    }

    /**
     * ArrayStorage specific exceptions
     */
    public class StorageOverflowException extends RuntimeException {}
    public class DuplicateElementException extends RuntimeException {}
    public class AbsentElementException extends RuntimeException {}
}
