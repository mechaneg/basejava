import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {

    private static final int MAX_SIZE = 10_000;
    private Resume[] storage = new Resume[MAX_SIZE];
    private int curSize = 0;

    public void clear() {
        Arrays.fill(storage, 0, curSize, null);
        curSize = 0;
    }

    public void save(Resume resume) {
        if (resume == null) {
            System.out.println("Unable to save null resume");
        }
        if (findPosition(resume.getUuid()) != -1) {
            System.out.println("Save: resume with uuid '" + resume.getUuid() + "' already exists");
        }
        if (curSize == MAX_SIZE) {
            System.out.println("Reached max size of array storage");
        }

        storage[curSize] = resume;
        curSize++;
    }

    public Resume get(String uuid) {
        int resumePos = findPosition(uuid);
        if (resumePos == -1) {
            System.out.println("Get: resume with uuid '" + uuid + "' doesn't exist");
            return null;
        } else {
            return storage[resumePos];
        }
    }

    public void delete(String uuid) {
        int resumePos = findPosition(uuid);
        if (resumePos == -1) {
            System.out.println("Delete: resume with uuid '" + uuid + "' doesn't exist");
        } else {
            storage[resumePos] = storage[curSize - 1];
            storage[curSize - 1] = null;
            curSize--;
        }
    }

    public void update(Resume resume) {
        int resumePos = findPosition(resume.getUuid());
        if (resumePos == -1) {
            System.out.println("Update: resume with uuid '" + resume.getUuid() + "' doesn't exist");
        } else {
            storage[resumePos] = resume;
        }
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
     * Finds storage position of corresponding resume with this uuid.
     * If there is no such resume returns -1.
     */
    private int findPosition(String uuid) {
        for (int i = 0; i < curSize; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
