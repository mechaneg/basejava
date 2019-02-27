package ru.mechaneg.basejava.storage;

import org.junit.jupiter.api.BeforeEach;

class ArrayStorageTest extends AbstractArrayStorageTest {
    @BeforeEach
    protected void createStorage() {
        this.storage = new ArrayStorage();
    }
}