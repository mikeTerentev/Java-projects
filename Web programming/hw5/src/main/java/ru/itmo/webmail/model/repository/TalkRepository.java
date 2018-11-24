package ru.itmo.webmail.model.repository;

import ru.itmo.webmail.model.domain.Talk;

import java.util.List;

public interface TalkRepository {
    void addTalk(long sourceUserId, long targetUserLogin, String text);

    List<Talk> findAll(long id);
}
