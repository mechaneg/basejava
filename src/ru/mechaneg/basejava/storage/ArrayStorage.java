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
    protected void deleteAtPosition(int position) {
        storage[position] = storage[curSize - 1];
        storage[curSize - 1] = null;
        curSize--;
    }

    @Override
    protected int prepareToInsert(int position) {
        assert position < 0;
        return curSize;
    }
}
