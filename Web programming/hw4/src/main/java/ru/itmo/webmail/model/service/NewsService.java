package ru.itmo.webmail.model.service;

import ru.itmo.webmail.model.domain.News;
import ru.itmo.webmail.model.domain.user.UserNews;
import ru.itmo.webmail.model.repository.NewsRepository;
import ru.itmo.webmail.model.repository.UserRepository;
import ru.itmo.webmail.model.repository.impl.NewsRepositoryImpl;
import ru.itmo.webmail.model.repository.impl.UserRepositoryImpl;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class NewsService {


    private NewsRepository newsRepository = new NewsRepositoryImpl();
    private UserRepository userRepository = new UserRepositoryImpl();

    public void addNews(long id, String text) {
        newsRepository.save(new News(id, text));
    }

    public List<UserNews> findAll() {

        List<UserNews> result = newsRepository.findAll().stream()
                .map(n -> new UserNews(userRepository.findUser(n.getUserId()).getLogin(), n.getText())).collect(Collectors.toList());
        Collections.reverse(result);
        return result;
    }
}
