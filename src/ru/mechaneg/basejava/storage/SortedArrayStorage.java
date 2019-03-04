package ru.mechaneg.basejava.storage;

import ru.mechaneg.basejava.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    /**
     * @return Imitates Arrays.binarySearch behaviour
     * <p>
     */
    @Override
    protected int findPosition(String uuid) {
        return Arrays.binarySearch(storage, 0, curSize, new Resume(uuid));
    }

    @Override
    protected int prepareToInsert(int position) {
        position = -(position + 1);
        System.arraycopy(storage, position, storage, position + 1, curSize - position);
        return position;
    }

    @Override
    protected void doDeleteAtPosition(int position) {
        System.arraycopy(storage, position + 1, storage, position, curSize - position - 1);
    }
}
