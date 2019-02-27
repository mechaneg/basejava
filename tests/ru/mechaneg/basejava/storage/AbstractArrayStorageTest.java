package ru.mechaneg.basejava.storage;

import org.junit.Before;
import org.junit.Test;
import ru.mechaneg.basejava.exception.ExistStorageException;
import ru.mechaneg.basejava.exception.NotExistStorageException;
import ru.mechaneg.basejava.exception.StorageOverflowException;
import ru.mechaneg.basejava.model.Resume;

import static org.junit.Assert.*;

public abstract class AbstractArrayStorageTest {

    private static final String UUID = "some uuid";
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";

    private IStorage storage;

    AbstractArrayStorageTest(IStorage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
    }

    @Test
    public void save() {
        storage.save(new Resume(UUID));
        assertEquals(storage.get(UUID).getUuid(), UUID);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(new Resume(UUID));
        storage.save(new Resume(UUID));
    }

    @Test(expected = StorageOverflowException.class)
    public void saveOverFlow() {
        storage.clear();
        try {
            for (int i = 0; i < AbstractArrayStorage.MAX_SIZE; i++) {
                storage.save(new Resume(Integer.toString(i)));
            }
        } catch (StorageOverflowException ex) {
            fail("Early overflow");
        }

        storage.save(new Resume("extraUuid"));
    }

    @Test
    public void get() {
        assertEquals(storage.get(UUID_1).getUuid(), UUID_1);
    }

    @Test
    public void getAll() {
        Resume[] all = storage.getAll();

        assertEquals(all.length, 2);
        assertArrayEquals(
                all,
                new Resume[]{
                        new Resume(UUID_1),
                        new Resume(UUID_2)
                }
        );
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.save(new Resume(UUID));
        storage.delete(UUID);
        storage.get(UUID);
    }

    @Test
    public void clear() {
        storage.clear();
        assertEquals(storage.size(), 0);
    }

    @Test
    public void update() {
        Resume prev = new Resume(UUID);
        storage.save(prev);

        Resume cur = new Resume(UUID);
        storage.update(cur);

        assertSame(storage.get(UUID), cur);
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(new Resume(UUID));
    }

    @Test
    public void size() {
        assertEquals(storage.size(), 2);
    }
}
