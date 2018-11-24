package ru.itmo.webmail.model.repository.impl;

import ru.itmo.webmail.model.database.DatabaseUtils;
import ru.itmo.webmail.model.domain.Talk;
import ru.itmo.webmail.model.exception.RepositoryException;
import ru.itmo.webmail.model.repository.TalkRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TalkRepositoryImpl implements TalkRepository {
    private static final DataSource DATA_SOURCE = DatabaseUtils.getDataSource();

    @Override
    public void addTalk(long sourceUserId, long targetUserLogin, String text) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO Talk (sourceUserId,targetUserId,text, creationTime) VALUES ( ?, ?,?, NOW())",
                    Statement.RETURN_GENERATED_KEYS)) {
                statement.setLong(1, sourceUserId);
                statement.setLong(2, targetUserLogin);
                statement.setString(3, text);
                if (statement.executeUpdate() != 1) {
                    throw new RepositoryException("Can't add Task.");
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't add Talk.", e);
        }
    }

    @Override
    public List<Talk> findAll(long id) {
        List<Talk> talks = new ArrayList<>();
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM Talk WHERE sourceUserId=? OR targetUserId=? ORDER BY creationTime DESC")) {
                statement.setLong(1, id);
                statement.setLong(2, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        talks.add(toTalk(statement.getMetaData(), resultSet));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find all users.", e);
        }
        return talks;
    }

    private Talk toTalk(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        Talk talk = new Talk();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            String columnName = metaData.getColumnName(i);
            if ("id".equalsIgnoreCase(columnName)) {
                talk.setId(resultSet.getLong(i));
            } else if ("sourceUserId".equalsIgnoreCase(columnName)) {
                talk.setSourceUserId(resultSet.getLong(i));
            } else if ("targetUserId".equalsIgnoreCase(columnName)) {
                talk.setTargetUserId(resultSet.getLong(i));
            } else if ("text".equalsIgnoreCase(columnName)) {
                talk.setText(resultSet.getString(i));
            } else if ("creationTime".equalsIgnoreCase(columnName)) {
                talk.setCreationTime(resultSet.getTimestamp(i));
            } else {
                throw new RepositoryException("Unexpected column 'Talk." + columnName + "'.");
            }
        }
        return talk;
    }
}
