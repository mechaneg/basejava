package ru.mechaneg.basejava.storage;

import ru.mechaneg.basejava.exception.NotExistStorageException;
import ru.mechaneg.basejava.model.Contact;
import ru.mechaneg.basejava.model.ContactType;
import ru.mechaneg.basejava.model.Resume;
import ru.mechaneg.basejava.sql.SqlQueryHelper;
import ru.mechaneg.basejava.util.Pair;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                    if (resume.getContacts().entrySet().isEmpty()) {
                        return null;
                    }

                    insertContacts(conn, resume);

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
                                    new Contact(resultQuery.getString("value"))
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
        List<Resume> resumes = queryHelper.executeQuery(
                "SELECT * FROM resume ORDER BY full_name, uuid",
                ps -> {
                    ResultSet result = ps.executeQuery();

                    List<Resume> resumesNoContacts = new ArrayList<>();
                    while (result.next()) {
                        resumesNoContacts.add(new Resume(result.getString("uuid"), result.getString("full_name")));
                    }
                    return resumesNoContacts;
                }
        );

        Map<String, List<Pair<ContactType, Contact>>> uuidToContacts = queryHelper.executeQuery(
                "SELECT * FROM contact",
                ps -> {
                    ResultSet result = ps.executeQuery();

                    Map<String, List<Pair<ContactType, Contact>>> contacts = new HashMap<>();
                    while (result.next()) {
                        String uuid = result.getString("resume_uuid");

                        if (!contacts.containsKey(uuid)) {
                            contacts.put(uuid, new ArrayList<>());
                        }

                        contacts.get(uuid).add(
                                new Pair<>(
                                        ContactType.valueOf(result.getString("type")),
                                        new Contact(result.getString("value"))
                                )
                        );
                    }
                    return contacts;
                }
        );

        for (Resume resume : resumes) {
            List<Pair<ContactType, Contact>> contacts = uuidToContacts.get(resume.getUuid());
            if (contacts == null) {
                continue;
            }

            for (Pair<ContactType, Contact> typedContact: contacts) {
                resume.setContact(typedContact.getFirst(), typedContact.getSecond());
            }
        }

        return resumes;
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
            for (Map.Entry<ContactType, Contact> entry : resume.getContacts().entrySet()) {
                ps.setString(1, entry.getKey().toString());
                ps.setString(2, entry.getValue().getValue());
                ps.setString(3, resume.getUuid());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }
}
