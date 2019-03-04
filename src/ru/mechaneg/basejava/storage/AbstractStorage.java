package ru.mechaneg.basejava.storage;

import ru.mechaneg.basejava.exception.ExistStorageException;
import ru.mechaneg.basejava.exception.NotExistStorageException;
import ru.mechaneg.basejava.model.Resume;

public abstract class AbstractStorage implements IStorage {

    /**
     * Finds storage position of corresponding resume with this uuid.
     * If there is no such resume returns negative number.
     */
    protected abstract int findPosition(String uuid);

    protected abstract void deleteAtPosition(int position);

    protected abstract void updateElementInStorage(int position, Resume resume);

    protected abstract void addNewElementToStorage(int insertPosition, Resume resume);

    protected abstract void checkOverflow(String uuidOfResumeToAdd);

    @Override
    public void update(Resume resume) {
        int resumePos = findPosition(resume.getUuid());
        if (resumePos < 0) {
            throw new NotExistStorageException(resume.getUuid());
        }
        updateElementInStorage(resumePos, resume);
    }

    @Override
    public void save(Resume resume) {
        if (resume == null) {
            System.out.println("Unable to save null resume");
            return;
        }

        checkOverflow(resume.getUuid());

        int insertPos = findPosition(resume.getUuid());

        if (insertPos >= 0) {
            throw new ExistStorageException(resume.getUuid());
        }

        addNewElementToStorage(insertPos, resume);
    }

    @Override
    public void delete(String uuid) {
        int resumePos = findPosition(uuid);
        if (resumePos < 0) {
            throw new NotExistStorageException(uuid);
        }

        deleteAtPosition(resumePos);
    }
}
