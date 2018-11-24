package ru.itmo.webmail.model.service;

import com.google.common.hash.Hashing;
import ru.itmo.webmail.model.domain.User;
import ru.itmo.webmail.model.exception.ValidationException;
import ru.itmo.webmail.model.repository.EmailConformationRepository;
import ru.itmo.webmail.model.repository.EventRepository;
import ru.itmo.webmail.model.repository.UserRepository;
import ru.itmo.webmail.model.repository.impl.EmailConformationRegistryImpl;
import ru.itmo.webmail.model.repository.impl.EventRepositoryImpl;
import ru.itmo.webmail.model.repository.impl.UserRepositoryImpl;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

import static java.util.Objects.isNull;

@SuppressWarnings("UnstableApiUsage")
public class UserService {
    private static final String USER_PASSWORD_SALT = "dc3475f2b301851b";

    private UserRepository userRepository = new UserRepositoryImpl();
    private EventRepository eventRepository = new EventRepositoryImpl();
    private EmailConformationRepository  emailConfirmationRepository = new EmailConformationRegistryImpl();
    public void validateRegistration(User user, String password) throws ValidationException {
        if (user.getLogin() == null || user.getLogin().isEmpty()) {
            throw new ValidationException("Login is required");
        }
        if (!user.getLogin().matches("[a-z]+")) {
            throw new ValidationException("Login can contain only lowercase Latin letters");
        }

        if (userRepository.findByLogin(user.getLogin()) != null) {
            throw new ValidationException("Login is already in use");
        }
        if (userRepository.findByLEmail(user.getEmail()) != null) {
            throw new ValidationException("Email is already in use");
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
        if (user.getEmail().chars().filter(num -> num == '@').count() != 1) {
            throw new ValidationException("Invalid email");
        }
    }

    public void register(User user, String password) {
        String passwordSha = getPasswordSha(password);
        String secret = UUID.randomUUID().toString();
        //String secret="blablabla";
        userRepository.save(user, passwordSha);
        emailConfirmationRepository.save(userRepository.findByLogin(user.getLogin()).getId(), secret);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void validateEnter(String loginOrEmail, String password) throws ValidationException {
        if (loginOrEmail == null || loginOrEmail.isEmpty()) {
            throw new ValidationException("Login is required");
        }
        /*if (!login.matches("[a-z]+")) {
            throw new ValidationException("Login can contain only lowercase Latin letters");
        }
        if (login.length() > 8) {
            throw new ValidationException("Login can't be longer than 8");
        }*/

        if (password == null || password.isEmpty()) {
            throw new ValidationException("Password is required");
        }
        if (password.length() < 4) {
            throw new ValidationException("Password can't be shorter than 4");
        }
        if (password.length() > 32) {
            throw new ValidationException("Password can't be longer than 32");
        }

        User user = userRepository.findByEmailOrLogin(loginOrEmail, getPasswordSha(password));
        if (isNull(user)) {
            throw new ValidationException("Invalid login or password");
        }
        if (!user.isConfirmed()) {
            throw new ValidationException("Email is not confirmed");
        }
    }

    private String getPasswordSha(String password) {
        return Hashing.sha256().hashString(USER_PASSWORD_SALT + password,
                StandardCharsets.UTF_8).toString();
    }

    public User authorize(String loginOrEmail, String password) {
        User x = userRepository.findByEmailOrLogin(loginOrEmail, getPasswordSha(password));
        eventRepository.loginEvent(x.getId());
        return x;
    }

    public User find(long userId) {
        return userRepository.find(userId);
    }

    public void logout(long userId) {
        eventRepository.logoutEvent(userId);
    }

    public int confirm(String secret) {
        return userRepository.findIdBySecretCode(secret);
    }
}
