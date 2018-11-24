package ru.itmo.webmail.model.repository;

import ru.itmo.webmail.model.domain.User;

public interface EmailConformationRepository {
    void save(long userId,String secret);
}
