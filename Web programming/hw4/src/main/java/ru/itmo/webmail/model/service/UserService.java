package ru.itmo.webmail.model.service;

import com.google.common.hash.Hashing;
import ru.itmo.webmail.model.domain.User;
import ru.itmo.webmail.model.exception.ValidationException;
import ru.itmo.webmail.model.repository.UserRepository;
import ru.itmo.webmail.model.repository.impl.UserRepositoryImpl;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class UserService {
    private static final String USER_PASSWORD_SALT = "dc3475f2b301851b";

    private UserRepository userRepository = new UserRepositoryImpl();


    public void validateRegistration(User user, String password, String congf_pass, String email) throws ValidationException {
        if (user.getLogin() == null || user.getLogin().isEmpty()) {
            throw new ValidationException("Login is required");
        }
        if (email.chars().filter(num -> num == '@').count() != 1) {
            throw new ValidationException("Invalid email");
        }
        if (!password.equals(congf_pass)) {
            throw new ValidationException("Password confirmation is falled");
        }
        if (!user.getLogin().matches("[a-z]+")) {
            throw new ValidationException("Login can contain only lowercase Latin letters");
        }
        if (user.getLogin().length() > 8) {
            throw new ValidationException("Login can't be longer than 8");
        }
        if (userRepository.findByLogin(user.getLogin()) != null) {
            throw new ValidationException("Login is already in use");
        }

        if (password == null || password.isEmpty()) {
            throw new ValidationException("Password is required");
        }
        if (password.length() < 4) {
            throw new ValidationException("Password can't be shorter than 4");
        }
        if (password.length() > 32) {
            throw new ValidationException("Password can't be longer than 32");
        }
    }

    public void register(User user, String password, String email) {
        user.setPasswordSha1(Hashing.sha256().hashString(USER_PASSWORD_SALT + password,
                StandardCharsets.UTF_8).toString());
        user.setEmail(email);
        user.setId(findCount() + 1);
        //List<User> x = findAll();
        userRepository.save(user);
    }

    public User login(String login, String password) throws ValidationException {
        User user = userRepository.findByLoginOrEmail(login);
        if (user != null
                && getPasswordSha1(password).equals(user.getPasswordSha1())) {
            return user;
        }
        throw new ValidationException("Incorrect login or password");
    }

    private String getPasswordSha1(String password) {
        return Hashing.sha256()
                .hashString(USER_PASSWORD_SALT + password, StandardCharsets.UTF_8)
                .toString();
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public long findCount() {
        return findAll().size();
    }


}
