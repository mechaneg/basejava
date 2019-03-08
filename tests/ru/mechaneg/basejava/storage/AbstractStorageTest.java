package ru.mechaneg.basejava.storage;

import org.junit.Before;
import org.junit.Test;
import ru.mechaneg.basejava.exception.ExistStorageException;
import ru.mechaneg.basejava.exception.NotExistStorageException;
import ru.mechaneg.basejava.model.Resume;

import static org.junit.Assert.*;

public abstract class AbstractStorageTest {

    private static final String UUID = "some uuid";
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";

    private final Resume resume = new Resume(UUID);
    private final Resume resume1 = new Resume(UUID_1);
    private final Resume resume2 = new Resume(UUID_2);

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
    public void getAll() {
        Resume[] all = storage.getAll();
        assertEquals(all.length, 2);
        boolean equals12 = all[0].equals(resume1) && all[1].equals(resume2);
        boolean equals21 = all[0].equals(resume2) && all[1].equals(resume1);
        assertTrue(equals12 || equals21);
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
        Resume cur = new Resume(UUID_1);
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