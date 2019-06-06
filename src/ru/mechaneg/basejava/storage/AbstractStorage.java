package ru.mechaneg.basejava.storage;

import ru.mechaneg.basejava.exception.ExistStorageException;
import ru.mechaneg.basejava.exception.NotExistStorageException;
import ru.mechaneg.basejava.model.Resume;

import java.util.Comparator;
import java.util.List;

public abstract class AbstractStorage<SK> implements IStorage {

    private static final Comparator<Resume> FULLNAME_UUID_RESUME_CMP =
            Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid);

    /**
     * Finds id of corresponding resume with this uuid.
     */
    protected abstract SK findSearchKey(String uuid);

    protected abstract Resume getBySearchKey(SK searchKey);

    protected abstract void deleteBySearchKey(SK searchKey);

    protected abstract void updateBySearchKey(SK searchKey, Resume resume);

    protected abstract void addNew(SK searchKey, Resume resume);

    protected abstract boolean isSearchKeyExist(SK searchKey);

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

        SK searchKey = findSearchKey(resume.getUuid());

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

    private SK findSearchKeyAssertExists(String uuid) {
        SK searchKey = findSearchKey(uuid);
        if (!isSearchKeyExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }
}
