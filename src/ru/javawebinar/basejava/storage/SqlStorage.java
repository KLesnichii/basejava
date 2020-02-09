package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.ContactType;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.sql.PreparedStatementExecutor;
import ru.javawebinar.basejava.sql.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
        sqlHelper.transactionalExecute(connection -> {
            doModification(
                    "INSERT INTO resume (full_name, uuid) VALUES (?,?)",
                    resume, connection, preparedStatement -> setParameter(preparedStatement, resume.getFullName(), resume.getUuid()));
            return doModificationBatch("INSERT INTO contact (value, resume_uuid, type) VALUES (?,?,?)",
                    connection, preparedStatement -> {
                        for (Map.Entry<ContactType, String> entry : resume.getContacts().entrySet()) {
                            setParameter(preparedStatement, entry.getValue(), resume.getUuid(), entry.getKey().name());
                        }
                    });
        });
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.transactionalExecute(connection -> {
            doModification(
                    "UPDATE resume  SET full_name = ? WHERE uuid = ?",
                    resume, connection, preparedStatement -> setParameter(preparedStatement, resume.getFullName(), resume.getUuid()));
            int[] updateCounts = doModificationBatch("" +
                            "UPDATE contact SET value = ? " +
                            "WHERE resume_uuid = ? " +
                            "AND type = ?",
                    connection, preparedStatement -> {
                        for (Map.Entry<ContactType, String> entry : resume.getContacts().entrySet()) {
                            setParameter(preparedStatement, entry.getValue(), resume.getUuid(), entry.getKey().name());
                        }
                    });
            Set<ContactType> contactTypeSet = resume.getContacts().keySet();
            doModificationBatch("INSERT INTO contact (value, resume_uuid, type) VALUES (?,?,?)",
                    connection, preparedStatement -> {
                        for (int i = 0; i < updateCounts.length; i++) {
                            if (updateCounts[i] == 0) {
                                ContactType contactType = new ArrayList<>(contactTypeSet).get(i);
                                setParameter(preparedStatement, resume.getContacts().get(contactType), resume.getUuid(), contactType.name());
                            }
                        }
                    });
            doModification("DELETE FROM contact c " +
                            "WHERE c.resume_uuid = ? " +
                            "AND c.type != ALL(?)", resume,
                    connection, preparedStatement -> {
                        preparedStatement.setString(1, resume.getUuid());
                        preparedStatement.setArray(2, connection.createArrayOf("VARCHAR",
                                contactTypeSet.stream()
                                        .map(Enum::name)
                                        .toArray()));
                    });
            return updateCounts;
        });
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
        return sqlHelper.execute("SELECT * FROM resume r LEFT JOIN contact c ON r.uuid = c.resume_uuid ORDER BY r.full_name, r.uuid", preparedStatement -> {
            List<Resume> resumeList = new ArrayList<>();
            ResultSet rs = preparedStatement.executeQuery();
            String uuid = "";
            Resume resume = new Resume();
            while (rs.next()) {
                String checkUuid = rs.getString("uuid");
                if (!checkUuid.equals(uuid)) {
                    uuid = checkUuid;
                    String fullName = rs.getString("full_name");
                    resume = new Resume(uuid, fullName);
                    resumeList.add(resume);
                }
                String type = rs.getString("type");
                if (type != null) {
                    String value = rs.getString("value");
                    resume.addContact(ContactType.valueOf(type), value);
                }
            }
            return resumeList;
        });
    }

    @Override
    public int size() {
        return sqlHelper.execute("SELECT count(r.uuid) FROM resume r", preparedStatement -> {
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            return rs.getInt(1);
        });
    }

    private void doModification(String queryResume, Resume resume, Connection connection, PreparedStatementExecutor pse) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(queryResume)) {
            pse.execute(preparedStatement);
            checkNotExistStorageException(resume.getUuid(), preparedStatement);
        }
    }

    private int[] doModificationBatch(String queryContact, Connection connection, PreparedStatementExecutor pse) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(queryContact)) {
            pse.execute(preparedStatement);
            return preparedStatement.executeBatch();
        }
    }

    private static void setParameter(PreparedStatement preparedStatement, String... parameter) throws SQLException {
        preparedStatement.setString(1, parameter[0]);
        preparedStatement.setString(2, parameter[1]);
        if (parameter.length == 3) {
            preparedStatement.setString(3, parameter[2]);
            preparedStatement.addBatch();
        }
    }

    private static void checkNotExistStorageException(String uuid, PreparedStatement preparedStatement) throws SQLException {
        if (preparedStatement.executeUpdate() == 0) {
            throw new NotExistStorageException(uuid);
        }
    }
}

