package ru.mechaneg.basejava.storage;

import ru.mechaneg.basejava.util.Config;

public class SqlStorageTest extends AbstractStorageTest {
    public SqlStorageTest() {
        super(new SqlStorage(
                Config.get().getDbUrl(),
                Config.get().getDbUser(),
                Config.get().getDbPassword()
        ));
    }
}
