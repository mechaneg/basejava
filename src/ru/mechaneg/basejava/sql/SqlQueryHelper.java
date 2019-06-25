package ru.mechaneg.basejava.sql;

import ru.mechaneg.basejava.exception.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class SqlQueryHelper {
    private final ConnectionFactory connectionFactory;

    public SqlQueryHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public <T> T executeQuery(String queryMasked, PreparedStatementProcessor<T> processor) {
        try (Connection conn = connectionFactory.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(queryMasked)) {
                return processor.process(ps);
            }
        } catch (SQLException ex) {
            throw ExceptionUtil.convertException(ex);
        }
    }

    public <T> T transactionalExecute(SqlTransaction<T> executor) {
        try (Connection conn = connectionFactory.getConnection()) {
            try {
                conn.setAutoCommit(false);
                T res = executor.execute(conn);
                conn.commit();
                return res;
            } catch (SQLException ex) {
                conn.rollback();
                throw ExceptionUtil.convertException(ex);
            }
        } catch (SQLException ex) {
            throw new StorageException(ex);
        }
    }
}