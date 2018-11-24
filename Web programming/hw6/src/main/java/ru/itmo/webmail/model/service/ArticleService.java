package ru.itmo.webmail.model.service;

import ru.itmo.webmail.model.domain.Article;
import ru.itmo.webmail.model.domain.User;
import ru.itmo.webmail.model.exception.ValidationException;
import ru.itmo.webmail.model.repository.ArticleRepository;
import ru.itmo.webmail.model.repository.impl.ArticleRepositoryImpl;

import java.util.List;
import java.util.stream.Collectors;


public class ArticleService {
    public ArticleRepository articleRepository = new ArticleRepositoryImpl();

    public void validateArticle(Article article, long userId) throws ValidationException {
        if (article.getText().equals("")) {
            throw new ValidationException("Write text");
        }
        if (article.getTitle().equals("")) {
            throw new ValidationException("Write title");
        }
    }

    public void addAtricle(Article article, long userId) {
        articleRepository.addAtricle(article, userId);
    }

    private List<Article> findAll() {
        return articleRepository.findAll();
    }

    public List<Article> findPublic() {
        return findAll().stream().filter(x-> !x.isHidden()).collect(Collectors.toList());
    }

    public  List<Article>  findPrivate(long userId) {
        List<Article> z = findAll().stream().filter(x-> x.getUserId() == userId).collect(Collectors.toList());
        return z;
    }

    public boolean changeMod(User user, long titleid) {
        articleRepository.changeMod(user,titleid);
        return articleRepository.find(titleid).isHidden();
    }
}
