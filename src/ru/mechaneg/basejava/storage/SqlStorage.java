package ru.mechaneg.basejava.storage;

import ru.mechaneg.basejava.exception.NotExistStorageException;
import ru.mechaneg.basejava.model.ContactType;
import ru.mechaneg.basejava.model.Resume;
import ru.mechaneg.basejava.sql.SqlQueryHelper;

import java.sql.*;
import java.util.*;

public class SqlStorage implements IStorage {
    private final SqlQueryHelper queryHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        this.queryHelper = new SqlQueryHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        queryHelper.executeQuery(
                "DELETE FROM resume",
                PreparedStatement::execute
        );
    }

    @Override
    public void save(Resume resume) {
        queryHelper.transactionalExecute(
                conn -> {
                    try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?, ?)")) {
                        ps.setString(1, resume.getUuid());
                        ps.setString(2, resume.getFullName());
                        ps.execute();
                    }

                    if (!resume.getContacts().entrySet().isEmpty()) {
                        insertContacts(conn, resume);
                    }

                    return null;
                }
        );
    }

    @Override
    public Resume get(String uuid) {
        return queryHelper.executeQuery(
                "SELECT * FROM resume r " +
                        " LEFT JOIN contact c" +
                        " ON r.uuid = c.resume_uuid" +
                        " WHERE r.uuid = ?",
                ps -> {
                    ps.setString(1, uuid);

                    ResultSet resultQuery = ps.executeQuery();
                    if (!resultQuery.next()) {
                        throw new NotExistStorageException(uuid);
                    }

                    Resume resume = new Resume(uuid, resultQuery.getString("full_name"));

                    do {
                        if (resultQuery.getString("type") != null) {
                            resume.setContact(
                                    ContactType.valueOf(resultQuery.getString("type")),
                                    resultQuery.getString("value")
                            );
                        }
                    } while (resultQuery.next());

                    return resume;
                }
        );
    }

    @Override
    public void delete(String uuid) {
        queryHelper.executeQuery(
                "DELETE FROM resume WHERE uuid = ?",
                ps -> {
                    ps.setString(1, uuid);

                    if (ps.executeUpdate() == 0) {
                        throw new NotExistStorageException(uuid);
                    }
                    return null;
                }
        );
    }

    @Override
    public void update(Resume resume) {
        queryHelper.transactionalExecute(
                conn -> {
                    try (PreparedStatement ps = conn.prepareStatement("UPDATE resume SET full_name = ? WHERE uuid = ?")) {
                        ps.setString(1, resume.getFullName());
                        ps.setString(2, resume.getUuid());

                        if (ps.executeUpdate() == 0) {
                            throw new NotExistStorageException(resume.getUuid());
                        }
                    }

                    try (PreparedStatement ps = conn.prepareStatement("DELETE FROM contact WHERE resume_uuid = ?")) {
                        ps.setString(1, resume.getUuid());
                        ps.execute();
                    }

                    insertContacts(conn, resume);

                    return null;
                }
        );
    }

    @Override
    public List<Resume> getAllSorted() {
        return queryHelper.transactionalExecute(
                conn -> {
                    Map<String, Resume> resumes = new LinkedHashMap<>();

                    try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume ORDER BY full_name, uuid")) {
                        ResultSet result = ps.executeQuery();
                        while (result.next()) {
                            resumes.put(
                                    result.getString("uuid"),
                                    new Resume(result.getString("uuid"), result.getString("full_name"))
                            );
                        }
                    }

                    try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM contact")) {
                        ResultSet result = ps.executeQuery();

                        while (result.next()) {
                            String uuid = result.getString("resume_uuid");

                            Resume resume = resumes.get(uuid);
                            Objects.requireNonNull(resume);

                            resume.setContact(
                                    ContactType.valueOf(result.getString("type")),
                                    result.getString("value")
                            );
                        }
                    }

                    return new ArrayList<>(resumes.values());
                }
        );
    }

    @Override
    public int size() {
        return queryHelper.executeQuery(
                "SELECT COUNT(*) FROM resume",
                ps -> {
                    ResultSet result = ps.executeQuery();

                    if (!result.next()) {
                        throw new IllegalStateException("Empty result of sql count");
                    }

                    return result.getInt(1);
                }
        );
    }

    private void insertContacts(Connection conn, Resume resume) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (type, value, resume_uuid) VALUES (?, ?, ?)")) {
            for (Map.Entry<ContactType, String> entry : resume.getContacts().entrySet()) {
                ps.setString(1, entry.getKey().toString());
                ps.setString(2, entry.getValue());
                ps.setString(3, resume.getUuid());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }
}
