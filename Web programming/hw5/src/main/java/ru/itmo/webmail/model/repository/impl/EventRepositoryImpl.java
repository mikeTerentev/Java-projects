package ru.itmo.webmail.model.repository.impl;

import ru.itmo.webmail.model.database.DatabaseUtils;
import ru.itmo.webmail.model.domain.EventType;
import ru.itmo.webmail.model.domain.User;
import ru.itmo.webmail.model.exception.RepositoryException;
import ru.itmo.webmail.model.repository.EventRepository;

import javax.sql.DataSource;
import java.sql.*;

public class EventRepositoryImpl implements EventRepository {
    private static final DataSource DATA_SOURCE = DatabaseUtils.getDataSource();

    @Override
    public void addEvent(long userId, EventType type) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO Event (userId, `type`, creationTime) VALUES ( ?, ?, NOW())",
                    Statement.RETURN_GENERATED_KEYS)) {
                statement.setLong(1, userId);
                statement.setString(2, type.toString());
                if (statement.executeUpdate() != 1) {
                    throw new RepositoryException("Can't add Event.");
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't add Event.", e);
        }
    }

    @Override
    public void logoutEvent(long userId) {
        addEvent(userId, EventType.LOGOUT);
    }

    @Override
    public void loginEvent(long userId) {
        addEvent(userId, EventType.ENTER);
    }
}
