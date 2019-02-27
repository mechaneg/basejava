package ru.mechaneg.basejava.storage;

import org.junit.Before;
import org.junit.Test;
import ru.mechaneg.basejava.exception.ExistStorageException;
import ru.mechaneg.basejava.exception.NotExistStorageException;
import ru.mechaneg.basejava.exception.StorageOverflowException;
import ru.mechaneg.basejava.model.Resume;

import java.util.Arrays;

import static org.junit.Assert.*;

public abstract class AbstractArrayStorageTest {

    static final String UUID = "some uuid";
    static final String UUID_1 = "uuid1";
    static final String UUID_2 = "uuid2";

    private IStorage storage;

    public AbstractArrayStorageTest(IStorage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
    }

    @Test
    public void saveGetSingle() {
        storage.save(new Resume(UUID));
        assertEquals(storage.get(UUID).getUuid(), UUID);
    }

    @Test
    public void saveMultiple() {
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));

        assertEquals(storage.get(UUID_1).getUuid(), UUID_1);
        assertEquals(storage.get(UUID_2).getUuid(), UUID_2);
    }

    @Test(expected = ExistStorageException.class)
    public void saveIdentical() {
        storage.save(new Resume(UUID));
        storage.save(new Resume(UUID));
    }

    private void saveLimitUpperBoundHelper() {
        final int LIMIT = 10_000;
        for (int i = 0; i < LIMIT; i++) {
            storage.save(new Resume(Integer.toString(i)));
        }
    }

    @Test
    public void saveLimitUpperBound() {
        saveLimitUpperBoundHelper();
    }

    @Test(expected = StorageOverflowException.class)
    public void saveLimitExceeded() {
        saveLimitUpperBoundHelper();
        storage.save(new Resume("extraUuid"));
    }

    @Test
    public void delete() {
        storage.save(new Resume(UUID));
        storage.delete(UUID);
        assertEquals(storage.size(), 0);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExisting() {
        storage.delete(UUID);
    }

    @Test
    public void clear() {
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.clear();

        assertArrayEquals(storage.getAll(), new Resume[]{});
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExisting() {
        storage.get(UUID);
    }

    @Test
    public void update() {
        Resume prev = new Resume(UUID);
        storage.save(prev);

        Resume cur = new Resume(UUID);
        storage.update(cur);

        assertTrue(storage.get(UUID) == cur);
    }

    @Test
    public void getAll() {
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_1));

        Resume[] all = storage.getAll();
        Arrays.sort(all);

        assertEquals(all.length, 2);
        assertArrayEquals(
                all,
                new Resume[]{
                        new Resume(UUID_1),
                        new Resume(UUID_2)
                }
        );
    }

    @Test
    public void size() {
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        assertEquals(storage.size(), 2);
    }

    @Test
    public void sizeEmpty() {
        assertEquals(storage.size(), 0);
    }
}
