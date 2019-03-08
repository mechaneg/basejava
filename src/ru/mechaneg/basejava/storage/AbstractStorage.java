package ru.mechaneg.basejava.storage;

import ru.mechaneg.basejava.exception.ExistStorageException;
import ru.mechaneg.basejava.exception.NotExistStorageException;
import ru.mechaneg.basejava.model.Resume;

public abstract class AbstractStorage implements IStorage {

    /**
     * Finds id of corresponding resume with this uuid.
     */
    protected abstract Object findId(String uuid);

    protected abstract Resume getAtId(Object id);

    protected abstract void deleteAtId(Object id);

    protected abstract void updateAtId(Object id, Resume resume);

    protected abstract void addNewAtId(Object id, Resume resume);

    protected abstract boolean idExists(Object id);

    private void assertIdExists(Object id, String uuid) {
        if (!idExists(id)) {
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    public Resume get(String uuid) {
        Object id = findId(uuid);
        assertIdExists(id, uuid);
        return getAtId(id);
    }

    @Override
    public void update(Resume resume) {
        Object id = findId(resume.getUuid());
        assertIdExists(id, resume.getUuid());
        updateAtId(id, resume);
    }

    @Override
    public void save(Resume resume) {
        if (resume == null) {
            System.out.println("Unable to save null resume");
            return;
        }

        Object id = findId(resume.getUuid());

        if (idExists(id)) {
            throw new ExistStorageException(resume.getUuid());
        }

        addNewAtId(id, resume);
    }

    @Override
    public void delete(String uuid) {
        Object id = findId(uuid);
        assertIdExists(id, uuid);
        deleteAtId(id);
    }
}
