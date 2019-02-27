package ru.mechaneg.basejava.storage;

import org.junit.jupiter.api.Test;
import ru.mechaneg.basejava.exception.ExistStorageException;
import ru.mechaneg.basejava.exception.NotExistStorageException;
import ru.mechaneg.basejava.exception.StorageOverflowException;
import ru.mechaneg.basejava.model.Resume;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public abstract class AbstractArrayStorageTest {

    static final String UUID = "some uuid";
    static final String UUID_1 = "uuid1";
    static final String UUID_2 = "uuid2";

    protected AbstractArrayStorage storage;

    @Test
    void saveGetSingle() {
        storage.save(new Resume(UUID));
        assertEquals(storage.get(UUID).getUuid(), UUID);
    }

    @Test
    void saveMultiple() {
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));

        assertEquals(storage.get(UUID_1).getUuid(), UUID_1);
        assertEquals(storage.get(UUID_2).getUuid(), UUID_2);
    }

    @Test
    void saveIdentical() {
        assertThrows(ExistStorageException.class, () -> {
            storage.save(new Resume(UUID));
            storage.save(new Resume(UUID));
        });
    }

    @Test
    void saveLimitExceeded() {
        final int LIMIT = 10_000;
        for (int i = 0; i < LIMIT; i++) {
            storage.save(new Resume(Integer.toString(i)));
        }

        assertThrows(StorageOverflowException.class, () -> {
            storage.save(new Resume(Integer.toString(LIMIT + 1)));
        });
    }

    @Test
    void delete() {
        storage.save(new Resume(UUID));
        storage.delete(UUID);
        assertEquals(storage.size(), 0);
    }

    @Test
    void deleteNotExisting() {
        assertThrows(NotExistStorageException.class, () -> {
            storage.delete(UUID);
        });
    }

    @Test
    void clear() {
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.clear();

        assertArrayEquals(storage.getAll(), new Resume[]{});
    }

    @Test
    void getNotExisting() {
        assertThrows(NotExistStorageException.class, () -> {
            storage.get(UUID);
        });
    }

    @Test
    void update() {
        Resume prev = new Resume(UUID);
        storage.save(prev);

        Resume cur = new Resume(UUID);
        storage.update(cur);

        assertTrue(storage.get(UUID) == cur);
    }

    @Test
    void getAll() {
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
    void size() {
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        assertEquals(storage.size(), 2);
    }

    @Test
    void sizeEmpty() {
        assertEquals(storage.size(), 0);
    }
}