package ru.mechaneg.basejava.storage;

import ru.mechaneg.basejava.exception.ExistStorageException;
import ru.mechaneg.basejava.exception.NotExistStorageException;
import ru.mechaneg.basejava.model.Resume;

public abstract class AbstractStorage implements IStorage {

    /**
     * Finds id of corresponding resume with this uuid.
     */
    protected abstract Object findSearchKey(String uuid);

    protected abstract Resume getBySearchKey(Object searchKey);

    protected abstract void deleteBySearchKey(Object searchKey);

    protected abstract void updateBySearchKey(Object searchKey, Resume resume);

    protected abstract void addNewForSearchKey(Object searchKey, Resume resume);

    protected abstract boolean searchKeyExists(Object searchKey);

    private Object findSearchKeyAssertExists(String uuid) {
        Object searchKey = findSearchKey(uuid);
        if (!searchKeyExists(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    @Override
    public Resume get(String uuid) {
        return getBySearchKey(findSearchKeyAssertExists(uuid));
    }

    @Override
    public void update(Resume resume) {
        updateBySearchKey(findSearchKeyAssertExists(resume.getUuid()), resume);
    }

    @Override
    public void save(Resume resume) {
        if (resume == null) {
            System.out.println("Unable to save null resume");
            return;
        }

        Object searchKey = findSearchKey(resume.getUuid());

        if (searchKeyExists(searchKey)) {
            throw new ExistStorageException(resume.getUuid());
        }

        addNewForSearchKey(searchKey, resume);
    }

    @Override
    public void delete(String uuid) {
        deleteBySearchKey(findSearchKeyAssertExists(uuid));
    }
}
