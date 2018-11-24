package ru.itmo.webmail.web.page;

import ru.itmo.webmail.model.domain.User;
import ru.itmo.webmail.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class NewsPage extends Page {
     void action(HttpServletRequest request){
         if((User) request.getSession().getAttribute("user")==null){
             throw new RedirectException("/enter");
         }
    }

    private void news(HttpServletRequest request, Map<String, Object> view) {
        User user = (User) request.getSession().getAttribute("user");
        long id = user.getId();
        String text =(String)request.getParameter("text");
        newsService.addNews(id,text);
        throw new RedirectException("/index");
    }
}
