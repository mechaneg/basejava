package ru.mechaneg.basejava.storage;

import ru.mechaneg.basejava.exception.NotExistStorageException;
import ru.mechaneg.basejava.model.Resume;
import ru.mechaneg.basejava.sql.SqlQueryHelper;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
                PreparedStatement::execute
        );
    }

    @Override
    public void save(Resume resume) {
        queryHelper.executeQuery(
                "INSERT INTO resume (uuid, full_name) VALUES (?, ?)",
                ps -> {
                    ps.setString(1, resume.getUuid());
                    ps.setString(2, resume.getFullName());
                    ps.execute();
                    return null;
                }
        );
    }

    @Override
    public Resume get(String uuid) {
        return queryHelper.executeQuery(
                "SELECT * FROM resume WHERE uuid = ?",
                ps -> {
                    ps.setString(1, uuid);

                    ResultSet resultQuery = ps.executeQuery();
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
        queryHelper.executeQuery(
                "UPDATE resume SET full_name = ? WHERE uuid = ?",
                ps -> {
                    ps.setString(1, resume.getFullName());
                    ps.setString(2, resume.getUuid());

                    if (ps.executeUpdate() == 0) {
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
                ps -> {
                    ResultSet result = ps.executeQuery();

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
                ps -> {
                    ResultSet result = ps.executeQuery();

                    if (!result.next()) {
                        throw new IllegalStateException("Empty result of sql count");
                    }

                    return result.getInt(1);
                }
        );
    }
}
