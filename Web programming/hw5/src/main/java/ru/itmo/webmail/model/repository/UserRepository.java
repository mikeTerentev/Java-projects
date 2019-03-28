package ru.itmo.webmail.model.repository;

import ru.itmo.webmail.model.domain.User;

import java.util.List;

public interface UserRepository {
    User findByEmailOrLogin(String loginOrEmail, String passwordSha);
    User findByEmailAndPasswordSha(String email, String passwordSha);
    User find(long userId);
    User findByLogin(String login);
    User findByLoginAndPasswordSha(String login, String passwordSha);
    List<User> findAll();
    void save(User user,String passwordSha);
    User findByLEmail(String email);

    int findIdBySecretCode(String secret);
}
