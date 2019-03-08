package ru.mechaneg.basejava.storage;

import org.junit.Test;
import ru.mechaneg.basejava.exception.StorageOverflowException;
import ru.mechaneg.basejava.model.Resume;

import static org.junit.Assert.fail;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {

    protected AbstractArrayStorageTest(IStorage storage) {
        super(storage);
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
}
