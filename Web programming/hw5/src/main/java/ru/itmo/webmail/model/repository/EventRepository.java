package ru.itmo.webmail.model.repository;

import ru.itmo.webmail.model.domain.EventType;
import ru.itmo.webmail.model.domain.User;

public interface EventRepository {
    void loginEvent(long userId);

    void addEvent(long userid, EventType type);

    void logoutEvent(long userId);
}
