package ru.mechaneg.basejava.storage;

import org.junit.Test;
import ru.mechaneg.basejava.util.Config;

import static org.junit.Assert.*;

public class SqlStorageTest extends AbstractStorageTest {
    public SqlStorageTest() {
        super(new SqlStorage(
                Config.get().getDbUrl(),
                Config.get().getDbUser(),
                Config.get().getDbPassword()
        ));
    }
}
