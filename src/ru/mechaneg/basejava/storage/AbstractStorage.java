package ru.mechaneg.basejava.storage;

import ru.mechaneg.basejava.exception.ExistStorageException;
import ru.mechaneg.basejava.exception.NotExistStorageException;
import ru.mechaneg.basejava.model.Resume;

import java.util.*;

public abstract class AbstractStorage implements IStorage {

    private static final Comparator<Resume> FULNAME_RESUME_CMP = new Comparator<Resume>() {
        @Override
        public int compare(Resume lhs, Resume rhs) {
            int fullNameCmpResult = lhs.getFullName().compareTo(rhs.getFullName());
            if (fullNameCmpResult == 0) {
                return lhs.getUuid().compareTo(rhs.getUuid());
            }
            return fullNameCmpResult;
        }
    };

    /**
     * Finds id of corresponding resume with this uuid.
     */
    protected abstract Object findSearchKey(String uuid);

    protected abstract Resume getBySearchKey(Object searchKey);

    protected abstract void deleteBySearchKey(Object searchKey);

    protected abstract void updateBySearchKey(Object searchKey, Resume resume);

    protected abstract void addNewForSearchKey(Object searchKey, Resume resume);

    protected abstract boolean searchKeyExists(Object searchKey);

    protected abstract Resume[] getAll();

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

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> sortedResumes = new ArrayList<>(Arrays.asList(getAll()));
        Collections.sort(sortedResumes, FULNAME_RESUME_CMP);
        return sortedResumes;
    }
}
