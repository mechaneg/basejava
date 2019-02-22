package ru.mechaneg.basejava.storage;

import ru.mechaneg.basejava.model.Resume;

public class ArrayStorage extends AbstractArrayStorage {

    /**
     * Complexity:
     * O(1)
     */
    @Override
    public void save(Resume resume) {
        if (resume == null) {
            System.out.println("Unable to save null resume");
        } else if (findPosition(resume.getUuid()) != -1) {
            System.out.println("Save: resume with uuid '" + resume.getUuid() + "' already exists");
        } else if (curSize == MAX_SIZE) {
            System.out.println("Reached max size of array storage");
        } else {
            storage[curSize] = resume;
            curSize++;
        }
    }

    /**
     * Complexity:
     * O(1)
     */
    @Override
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

    /**
     * Complexity:
     * O(N)
     */
    @Override
    protected int findPosition(String uuid) {
        for (int i = 0; i < curSize; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
