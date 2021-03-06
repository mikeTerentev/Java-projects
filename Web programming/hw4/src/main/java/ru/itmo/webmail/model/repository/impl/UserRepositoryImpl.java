package ru.itmo.webmail.model.repository.impl;

import ru.itmo.webmail.model.domain.User;
import ru.itmo.webmail.model.repository.UserRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {
    private static final File tmpDir = new File(System.getProperty("java.io.tmpdir"));

    private List<User> users;
    private long maxId;
    public UserRepositoryImpl() {
        try {
            //noinspection unchecked
            users = (List<User>) new ObjectInputStream(
                    new FileInputStream(new File(tmpDir, getClass().getSimpleName()))).readObject();
            maxId = users.size() + 1;
        } catch (Exception e) {
            users = new ArrayList<>();
        }
    }

    @Override
    public void save(User user) {
        users.add(user);
        user.setId(++maxId);
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                    new FileOutputStream(new File(tmpDir, getClass().getSimpleName())));
            objectOutputStream.writeObject(users);
            objectOutputStream.close();
        } catch (Exception e) {
            throw new RuntimeException("Can't save user.", e);
        }
    }

    @Override
    public User findByLogin(String login) {
        return users.stream().filter(user -> user.getLogin().equals(login)).findFirst().orElse(null);
    }
    @Override
    public User findUser(long id) {
        return users.stream().filter(user -> user.getId() == id).findFirst().orElse(null);
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(users);
    }

    @Override
    public User findByLoginOrEmail(String login) {
        return users.stream()
                .filter(user -> user.getEmail().equals(login))
                .findFirst()
                .orElse(findByLogin(login));
    }
}
