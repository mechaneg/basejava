package ru.mechaneg.basejava.storage;

public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected int findPosition(String uuid) {
        for (int i = 0; i < curSize; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected int prepareToInsert(int position) {
        return curSize;
    }

    @Override
    protected void doDeleteAtPosition(int position) {
        storage[position] = storage[curSize - 1];
    }
}
