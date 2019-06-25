package ru.mechaneg.basejava.sql;

import ru.mechaneg.basejava.exception.ExistStorageException;
import ru.mechaneg.basejava.exception.StorageException;

import java.sql.SQLException;

public class ExceptionUtil {
    public static StorageException convertException(SQLException ex) {
        if (ex.getSQLState().equals("23505")) {
            /*this state corresponds to duplicate key insertion*/
            throw new ExistStorageException(ex);
        }
        throw new IllegalStateException(ex);
    }
}
