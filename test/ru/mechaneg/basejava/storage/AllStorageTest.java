package ru.mechaneg.basejava.storage;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ArrayStorageTest.class,
        SortedArrayStorageTest.class,
        ListStorageTest.class,
        MapUuidStorageTest.class,
        MapResumeStorageTest.class,
        FileStorageTest.class,
        PathObjectStreamStorageTest.class,
        PathDataStreamStorageTest.class,
        PathJsonStorageTest.class,
        PathXmlStorageTest.class,
        SqlStorageTest.class
})
public class AllStorageTest {
}
