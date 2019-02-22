package ru.mechaneg.basejava.storage;

import ru.mechaneg.basejava.model.Resume;
import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    /**
     * Complexity:
     * O(N)
     */
    @Override
    public void delete(String uuid) {
        int resumePos = findPosition(uuid);
        if (resumePos < 0) {
            System.out.println("Delete: resume with uuid '" + uuid + "' doesn't exist");
            return;
        }

        for (int i = resumePos; i + 1 < curSize; i++) {
            storage[i] = storage[i + 1];
        }
        storage[curSize] = null;
        curSize--;
    }

    /**
     * Complexity:
     * O(N)
     */
    @Override
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

        insertPos = -(insertPos + 1);

        for (int i = curSize; i > insertPos; i--) {
            storage[i] = storage[i - 1];
        }

        storage[insertPos] = resume;
        curSize++;
    }

    /**
     * @return
     * Imitates Arrays.binarySearch behaviour
     *
     * Complexity:
     * O(log N)
     */
    @Override
    protected int findPosition(String uuid) {
        return Arrays.binarySearch(storage, 0, curSize, new Resume(uuid));
    }
}
