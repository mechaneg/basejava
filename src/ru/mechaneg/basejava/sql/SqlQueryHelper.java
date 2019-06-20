package ru.mechaneg.basejava.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class SqlQueryHelper {
    private final ConnectionFactory connectionFactory;

    public SqlQueryHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public void executeQuery(String queryMasked, QueryBuilder builder, QueryConsumer consumer) {
        try (Connection conn = connectionFactory.getConnection()) {
            try (PreparedStatement query = conn.prepareStatement(queryMasked)) {
                builder.accept(query);
                consumer.accept(query);
            }
        } catch (SQLException ex) {
            throw new IllegalStateException(ex);
        }
    }

    public <T> T executeQuery(String queryMasked, QueryBuilder builder, QueryConsumerReturning<T> consumer) {
        try (Connection conn = connectionFactory.getConnection()) {
            try (PreparedStatement query = conn.prepareStatement(queryMasked)) {
                builder.accept(query);
                return consumer.accept(query);
            }
        } catch (SQLException ex) {
            throw new IllegalStateException(ex);
        }
    }

    @FunctionalInterface
    public interface QueryBuilder {
        void accept(PreparedStatement query) throws SQLException;
    }

    @FunctionalInterface
    public interface QueryConsumer {
        void accept(PreparedStatement query) throws SQLException;
    }

    @FunctionalInterface
    public interface QueryConsumerReturning<T> {
        T accept(PreparedStatement query) throws SQLException;
    }
}