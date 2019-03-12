package ru.mechaneg.basejava.storage;

import org.junit.Before;
import org.junit.Test;
import ru.mechaneg.basejava.exception.ExistStorageException;
import ru.mechaneg.basejava.exception.NotExistStorageException;
import ru.mechaneg.basejava.model.Resume;

import java.util.List;

import static org.junit.Assert.*;

public abstract class AbstractStorageTest {

    private static final String UUID = "some uuid";
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";

    private final Resume resume = new Resume(UUID, "john");
    private final Resume resume1 = new Resume(UUID_1, "clint");
    private final Resume resume2 = new Resume(UUID_2, "briant");

    protected IStorage storage;

    protected AbstractStorageTest(IStorage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(resume1);
        storage.save(resume2);
    }

    @Test
    public void save() {
        storage.save(resume);
        assertSame(storage.get(UUID), resume);
        assertEquals(storage.size(), 3);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(resume1);
    }

    @Test
    public void get() {
        assertSame(storage.get(UUID_1), resume1);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get(UUID);
    }

    @Test
    public void getAllSorted() {
        List<Resume> all = storage.getAllSorted();
        assertEquals(all.size(), 2);
        assertEquals(all.get(0), resume2);
        assertEquals(all.get(1), resume1);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(UUID_1);
        assertEquals(storage.size(), 1);
        storage.get(UUID_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete(UUID);
    }

    @Test
    public void clear() {
        storage.clear();
        assertEquals(storage.size(), 0);
    }

    @Test
    public void update() {
        Resume cur = new Resume(UUID_1, "dimon");
        storage.update(cur);

        assertSame(storage.get(UUID_1), cur);
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(resume);
    }

    @Test
    public void size() {
        assertEquals(storage.size(), 2);
    }
}