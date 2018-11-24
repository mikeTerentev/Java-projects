package ru.itmo.webmail.model.repository;

import ru.itmo.webmail.model.domain.Article;
import ru.itmo.webmail.model.domain.User;

import java.util.List;

public interface ArticleRepository {
    List<Article>  findAll();
    void addAtricle(Article article, long userId);

    void changeMod(User user, long atricleId);

    Article find(long titleid);
}
