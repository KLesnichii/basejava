package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.sql.SqlHelper;

import java.sql.*;
import java.util.*;

public class SqlStorage implements Storage {
    private SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
            try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO resume (full_name, uuid) VALUES (?,?)")) {
                preparedStatement.setString(1, resume.getFullName());
                preparedStatement.setString(2, resume.getUuid());
                preparedStatement.executeUpdate();
            }
            insertIntoContact(resume, connection);
            insertIntoSection(resume, connection);
            return null;
        });
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.transactionalExecute(connection -> {
            try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE resume  SET full_name = ? WHERE uuid = ?")) {
                preparedStatement.setString(1, resume.getFullName());
                preparedStatement.setString(2, resume.getUuid());
                checkNotExistStorageException(resume.getUuid(), preparedStatement);
            }
            deleteFromResume(resume, connection, "DELETE FROM contact c " +
                    "WHERE c.resume_uuid = ? ");
            insertIntoContact(resume, connection);
            deleteFromResume(resume, connection, "DELETE FROM section s " +
                    "WHERE s.resume_uuid = ? ");
            insertIntoSection(resume, connection);
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.transactionalExecute(connection -> {
            Resume resume;
            try (PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "SELECT * FROM resume r " +
                    "WHERE r.uuid =?")) {
                preparedStatement.setString(1, uuid);
                ResultSet rsResume = preparedStatement.executeQuery();
                if (!rsResume.next()) {
                    throw new NotExistStorageException(uuid);
                }
                resume = new Resume(uuid, rsResume.getString("full_name"));
            }
            try (PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "SELECT c.type, c.value " +
                    "FROM contact c " +
                    "WHERE c.resume_uuid =?")) {
                preparedStatement.setString(1, uuid);
                ResultSet rsContact = preparedStatement.executeQuery();
                while (rsContact.next()) {
                    addContact(resume, rsContact);
                }
            }
            try (PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "SELECT s.type, s.value " +
                    "FROM section s " +
                    "WHERE s.resume_uuid =?")) {
                preparedStatement.setString(1, uuid);
                ResultSet rsSection = preparedStatement.executeQuery();
                while (rsSection.next()) {
                    addSection(resume, rsSection);
                }
            }
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
        return sqlHelper.transactionalExecute(connection -> {
            Map<String, Resume> resumeMap = new LinkedHashMap<>();
            try (PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "SELECT * FROM resume r " +
                    "ORDER BY r.full_name, r.uuid")) {
                ResultSet rsResume = preparedStatement.executeQuery();
                while (rsResume.next()) {
                    String uuid = rsResume.getString("uuid");
                    resumeMap.put(uuid, new Resume(uuid,
                            rsResume.getString("full_name")));
                }
            }
            try (PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "SELECT resume_uuid, type, value " +
                    "FROM contact")) {
                ResultSet rsContact = preparedStatement.executeQuery();
                while (rsContact.next()) {
                    addContact(resumeMap.get(rsContact.getString("resume_uuid")),
                            rsContact);
                }
            }
            try (PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "SELECT resume_uuid, type, value " +
                    "FROM section")) {
                ResultSet rsSection = preparedStatement.executeQuery();
                while (rsSection.next()) {
                    addSection(resumeMap.get(rsSection.getString("resume_uuid")),
                            rsSection);
                }
            }
            return new ArrayList<>(resumeMap.values());
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

    private void deleteFromResume(Resume resume, Connection connection, String query) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, resume.getUuid());
            preparedStatement.executeUpdate();
        }
    }

    private void insertIntoContact(Resume resume, Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO contact (value, resume_uuid, type) VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> entry : resume.getContacts().entrySet()) {
                preparedStatement.setString(1, entry.getValue());
                preparedStatement.setString(2, resume.getUuid());
                preparedStatement.setString(3, entry.getKey().name());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        }
    }

    private void insertIntoSection(Resume resume, Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO section (value, resume_uuid, type) VALUES (?,?,?)")) {
            for (Map.Entry<SectionType, Section> entry : resume.getSections().entrySet()) {
                switch (entry.getKey()) {
                    case OBJECTIVE:
                    case PERSONAL:
                        preparedStatement.setString(1, ((TextFieldSection) entry.getValue()).getText());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        String sectionText = String.join("\n", ((TextListSection) entry.getValue()).getTextList());
                        preparedStatement.setString(1, sectionText);
                        break;
                }
                preparedStatement.setString(2, resume.getUuid());
                preparedStatement.setString(3, entry.getKey().name());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        }
    }

    private void addContact(Resume resume, ResultSet rs) throws SQLException {
        String type = rs.getString("type");
        String value = rs.getString("value");
        if (value != null) {
            resume.addContact(ContactType.valueOf(type), value);
        }
    }

    private void addSection(Resume resume, ResultSet rs) throws SQLException {
        String type = rs.getString("type");
        String value = rs.getString("value");
        if (value != null) {
            SectionType sectionType = SectionType.valueOf(type);
            switch (sectionType) {
                case OBJECTIVE:
                case PERSONAL:
                    resume.addSection(sectionType, new TextFieldSection(value));
                    break;
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    resume.addSection(sectionType,
                            new TextListSection(Arrays.asList(value.split("\n"))));
                    break;
            }
        }
    }

    private static void checkNotExistStorageException(String uuid, PreparedStatement preparedStatement) throws SQLException {
        if (preparedStatement.executeUpdate() == 0) {
            throw new NotExistStorageException(uuid);
        }
    }
}

