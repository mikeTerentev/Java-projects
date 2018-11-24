package ru.itmo.webmail.model.repository.impl;

import ru.itmo.webmail.model.database.DatabaseUtils;
import ru.itmo.webmail.model.domain.User;
import ru.itmo.webmail.model.exception.RepositoryException;
import ru.itmo.webmail.model.repository.EmailConformationRepository;

import javax.sql.DataSource;
import java.sql.*;

public class EmailConformationRegistryImpl implements EmailConformationRepository {
    private static final DataSource DATA_SOURCE = DatabaseUtils.getDataSource();

    @Override
    public void save(long userId, String secret) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO EmailConfirmation (userId, secret, creationTime) VALUES ( ?, ?, NOW())",
                    Statement.RETURN_GENERATED_KEYS)) {
                statement.setLong(1, userId);
                statement.setString(2, secret);
                if (statement.executeUpdate() != 1) {
                    throw new RepositoryException("Can't add conformation code.");
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't add conformation code.", e);
        }
    }
}
