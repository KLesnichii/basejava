package ru.javawebinar.basejava.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface PreparedStatementExecutor {
    void execute(PreparedStatement preparedStatement) throws SQLException;
}
