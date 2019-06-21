package ru.mechaneg.basejava.storage;

import ru.mechaneg.basejava.exception.ExistStorageException;
import ru.mechaneg.basejava.exception.NotExistStorageException;
import ru.mechaneg.basejava.model.Resume;
import ru.mechaneg.basejava.sql.SqlQueryHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements IStorage {
    private final SqlQueryHelper queryHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        this.queryHelper = new SqlQueryHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        queryHelper.executeQuery(
                "DELETE FROM resume",
                query -> null,
                PreparedStatement::execute
        );
    }

    @Override
    public void save(Resume resume) {
        queryHelper.executeQuery(
                "INSERT INTO resume (uuid, full_name) VALUES (?, ?)",
                query -> {
                    query.setString(1, resume.getUuid());
                    query.setString(2, resume.getFullName());
                    return null;
                },
                query -> {
                    query.execute();
                    return null;
                }
        );
    }

    @Override
    public Resume get(String uuid) {
        return queryHelper.executeQuery(
                "SELECT * FROM resume WHERE uuid = ?",
                query -> {
                    query.setString(1, uuid);
                    return null;
                },
                query -> {
                    ResultSet resultQuery = query.executeQuery();
                    if (!resultQuery.next()) {
                        throw new NotExistStorageException(uuid);
                    }

                    return new Resume(uuid, resultQuery.getString("full_name"));
                }
        );
    }

    @Override
    public void delete(String uuid) {
        queryHelper.executeQuery(
                "DELETE FROM resume WHERE uuid = ?",
                query -> {
                    query.setString(1, uuid);
                    return null;
                },
                query -> {
                    if (query.executeUpdate() == 0) {
                        throw new NotExistStorageException(uuid);
                    }
                    return null;
                }
        );
    }

    @Override
    public void update(Resume resume) {
        queryHelper.executeQuery(
                "UPDATE resume SET full_name = ? WHERE uuid = ?",
                query -> {
                    query.setString(1, resume.getFullName());
                    query.setString(2, resume.getUuid());
                    return null;
                },
                query -> {
                    if (query.executeUpdate() == 0) {
                        throw new NotExistStorageException(resume.getUuid());
                    }
                    return null;
                }
        );
    }

    @Override
    public List<Resume> getAllSorted() {
        return queryHelper.executeQuery(
                "SELECT * FROM resume ORDER BY full_name, uuid",
                query -> null,
                query -> {
                    ResultSet result = query.executeQuery();

                    List<Resume> resumes = new ArrayList<>();
                    while (result.next()) {
                        resumes.add(new Resume(result.getString("uuid"), result.getString("full_name")));
                    }
                    return resumes;
                }
        );
    }

    @Override
    public int size() {
        return queryHelper.executeQuery(
                "SELECT COUNT(*) FROM resume",
                query -> null,
                query -> {
                    ResultSet result = query.executeQuery();

                    if (!result.next()) {
                        throw new IllegalStateException("Empty result of sql count");
                    }

                    return result.getInt(1);
                }
        );
    }
}
