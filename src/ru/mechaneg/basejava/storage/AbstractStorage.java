package ru.mechaneg.basejava.storage;

import ru.mechaneg.basejava.exception.ExistStorageException;
import ru.mechaneg.basejava.exception.NotExistStorageException;
import ru.mechaneg.basejava.model.Resume;

import java.util.Comparator;
import java.util.List;

public abstract class AbstractStorage implements IStorage {

    private static final Comparator<Resume> FULLNAME_UUID_RESUME_CMP =
            Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid);

    /**
     * Finds id of corresponding resume with this uuid.
     */
    protected abstract Object findSearchKey(String uuid);

    protected abstract Resume getBySearchKey(Object searchKey);

    protected abstract void deleteBySearchKey(Object searchKey);

    protected abstract void updateBySearchKey(Object searchKey, Resume resume);

    protected abstract void addNew(Object searchKey, Resume resume);

    protected abstract boolean isSearchKeyExist(Object searchKey);

    protected abstract List<Resume> getAll();

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

        if (isSearchKeyExist(searchKey)) {
            throw new ExistStorageException(resume.getUuid());
        }

        addNew(searchKey, resume);
    }

    @Override
    public void delete(String uuid) {
        deleteBySearchKey(findSearchKeyAssertExists(uuid));
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> sortedResumes = getAll();
        sortedResumes.sort(FULLNAME_UUID_RESUME_CMP);
        return sortedResumes;
    }

    private Object findSearchKeyAssertExists(String uuid) {
        Object searchKey = findSearchKey(uuid);
        if (!isSearchKeyExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }
}
