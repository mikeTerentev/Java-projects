package ru.itmo.webmail.web.page;

import ru.itmo.webmail.model.repository.NewsRepository;
import ru.itmo.webmail.model.repository.UserRepository;
import ru.itmo.webmail.model.repository.impl.UserRepositoryImpl;
import ru.itmo.webmail.model.service.NewsService;
import ru.itmo.webmail.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class Page {
    public void before(HttpServletRequest request, Map<String, Object> view){
        view.put("userCount",userService.findCount());
        view.put("news", newsService.findAll());
    }

   void action(Map<String, Object> view){

    }
   UserService userService = new UserService();
   NewsService newsService = new NewsService();
}
