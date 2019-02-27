package ru.mechaneg.basejava.storage;

import org.junit.jupiter.api.BeforeEach;

class SortedArrayStorageTest extends AbstractArrayStorageTest {
    @BeforeEach
    protected void createStorage() {
        this.storage = new SortedArrayStorage();
    }
}