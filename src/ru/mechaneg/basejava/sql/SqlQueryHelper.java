package ru.mechaneg.basejava.sql;

import ru.mechaneg.basejava.exception.ExistStorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class SqlQueryHelper {
    private final ConnectionFactory connectionFactory;

    public SqlQueryHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public <T> T executeQuery(String queryMasked, QueryProcessor<T> builder, QueryProcessor<T> consumer) {
        try (Connection conn = connectionFactory.getConnection()) {
            try (PreparedStatement query = conn.prepareStatement(queryMasked)) {
                builder.accept(query);
                return consumer.accept(query);
            }
        } catch (SQLException ex) {
            if (ex.getSQLState().equals("23505")) {
                /*this state corresponds to duplicate key insertion*/
                throw new ExistStorageException(ex);
            }
            throw new IllegalStateException(ex);
        }
    }

    @FunctionalInterface
    public interface QueryProcessor<T> {
        T accept(PreparedStatement query) throws SQLException;
    }
}