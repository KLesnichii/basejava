package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.ContactType;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.sql.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {
    private SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        this.sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume", preparedStatement -> {
            preparedStatement.execute();
            return null;
        });
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.transactionalExecute(connection -> doModificationResumes(
                "INSERT INTO resume (full_name, uuid) VALUES (?,?)",
                "INSERT INTO contact (value, resume_uuid, type) VALUES (?,?,?)",
                resume, connection));
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.transactionalExecute(connection -> doModificationResumes(
                "UPDATE resume  SET full_name = ? WHERE uuid = ?",
                "UPDATE contact SET value = ? WHERE resume_uuid = ? AND type = ?",
                resume, connection));
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.execute("" +
                "SELECT * FROM resume r " +
                "LEFT JOIN contact c " +
                "ON r.uuid = c.resume_uuid " +
                "WHERE r.uuid =?", preparedStatement -> {
            preparedStatement.setString(1, uuid);
            ResultSet rs = preparedStatement.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            Resume resume = new Resume(uuid, rs.getString("full_name"));
            do {
                String value = rs.getString("value");
                if (value != null) {
                    ContactType type = ContactType.valueOf(rs.getString("type"));
                    resume.addContact(type, value);
                }
            } while (rs.next());
            return resume;
        });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.execute("DELETE FROM resume r WHERE r.uuid =?", preparedStatement -> {
            preparedStatement.setString(1, uuid);
            checkNotExistStorageException(uuid, preparedStatement);
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> fullResumeList = new ArrayList<>();
        sqlHelper.execute("" +
                "SELECT * FROM resume r " +
                "ORDER BY r.full_name, r.uuid", preparedStatement -> {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                fullResumeList.add(new Resume(rs.getString("uuid"), rs.getString("full_name")));
            }
            return true;
        });
        sqlHelper.execute("" +
                "SELECT c.type, c.value FROM contact c " +
                "WHERE resume_uuid = ? ", preparedStatement -> {
            for (Resume resume : fullResumeList) {
                preparedStatement.setString(1, resume.getUuid());
                ResultSet rs = preparedStatement.executeQuery();
                while (rs.next()) {
                    String value = rs.getString("value");
                    ContactType type = ContactType.valueOf(rs.getString("type"));
                    resume.addContact(type, value);
                }
            }
            return true;
        });
        return fullResumeList;
    }

    @Override
    public int size() {
        return sqlHelper.execute("SELECT count(r.uuid) FROM resume r", preparedStatement -> {
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            return rs.getInt(1);
        });
    }

    private boolean doModificationResumes(String queryResume, String queryContact,
                                          Resume resume, Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(queryResume)) {
            preparedStatement.setString(1, resume.getFullName());
            preparedStatement.setString(2, resume.getUuid());
            checkNotExistStorageException(resume.getUuid(), preparedStatement);
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement(queryContact)) {
            for (Map.Entry<ContactType, String> entry : resume.getContacts().entrySet()) {
                preparedStatement.setString(1, entry.getValue());
                preparedStatement.setString(2, resume.getUuid());
                preparedStatement.setString(3, entry.getKey().name());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        }
        return true;
    }

    private static void checkNotExistStorageException(String uuid, PreparedStatement preparedStatement) throws SQLException {
        if (preparedStatement.executeUpdate() == 0) {
            throw new NotExistStorageException(uuid);
        }
    }
}

