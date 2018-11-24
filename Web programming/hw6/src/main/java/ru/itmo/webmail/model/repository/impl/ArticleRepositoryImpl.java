package ru.itmo.webmail.model.repository.impl;

import ru.itmo.webmail.model.database.DatabaseUtils;
import ru.itmo.webmail.model.domain.Article;
import ru.itmo.webmail.model.domain.User;
import ru.itmo.webmail.model.exception.RepositoryException;
import ru.itmo.webmail.model.repository.ArticleRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class ArticleRepositoryImpl implements ArticleRepository {
    private static DataSource DATA_SOURCE = DatabaseUtils.getDataSource();


    @Override
    public List<Article> findAll() {
        List<Article> articles = new ArrayList<>();
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM Article ORDER BY id DESC")) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        articles.add(toArticle(statement.getMetaData(), resultSet));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find  save article.", e);
        }
        return articles;
    }

    private Article toArticle(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        Article article = new Article();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            String columnName = metaData.getColumnName(i);
            if ("id".equalsIgnoreCase(columnName)) {
                article.setId(resultSet.getLong(i));
            } else if ("userId".equalsIgnoreCase(columnName)) {
                article.setUserId(resultSet.getLong(i));
            } else if ("title".equalsIgnoreCase(columnName)) {
                article.setTitle(resultSet.getString(i));
            } else if ("text".equalsIgnoreCase(columnName)) {
                article.setText(resultSet.getString(i));
            } else if ("creationTime".equalsIgnoreCase(columnName)) {
                article.setCreationTime(resultSet.getTimestamp(i));
            } else if ("hidden".equalsIgnoreCase(columnName)) {
                article.setHidden(resultSet.getBoolean(i));
            }else {
                throw new RepositoryException("Unexpected column 'User." + columnName + "'.");
            }
        }
        return article;
    }

    @Override
    public void addAtricle(Article article, long userId) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO Article (userId,title,text,creationTime) VALUES (?,?,?,NOW())",
                    Statement.RETURN_GENERATED_KEYS)) {
                statement.setLong(1, userId);
                statement.setString(2, article.getTitle());
                statement.setString(3, article.getText());
                if (statement.executeUpdate() == 1) {
                    ResultSet generatedIdResultSet = statement.getGeneratedKeys();
                    if (generatedIdResultSet.next()) {
                        article.setId(generatedIdResultSet.getLong(1));
                        article.setCreationTime(findCreationTime(article.getId()));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find  save article.", e);
        }
    }
    @Override
    public void changeMod(User user, long atricleId) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "UPDATE Article SET hidden = NOT hidden WHERE userId=? AND id=?")) {
                statement.setLong(1, user.getId());
                statement.setLong(2, atricleId);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find  change article mod.", e);
        }
    }

    @Override
    public Article find(long titleid) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM Article WHERE id=?")) {
                statement.setLong(1, titleid);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return toArticle(statement.getMetaData(), resultSet);
                    } else {
                        return null;
                    }
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find Article by id.", e);
        }
    }

    private Date findCreationTime(long userId) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "SELECT creationTime FROM Article WHERE id=?")) {
                statement.setLong(1, userId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getTimestamp(1);
                    }
                }
                throw new RepositoryException("Can't find User.creationTime by id.");
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find User.creationTime by id.", e);
        }
    }
}
