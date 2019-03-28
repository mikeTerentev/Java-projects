package ru.itmo.webmail.web.page;

import ru.itmo.webmail.model.domain.User;
import ru.itmo.webmail.model.repository.EventRepository;
import ru.itmo.webmail.model.service.TalkService;
import ru.itmo.webmail.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class Page {
    static final String USER_ID_SESSION_KEY = "userId";

    private UserService userService = new UserService();
    private TalkService talkService = new TalkService();

    private User user;
    TalkService getTalkService() {
        return talkService;
    }
    UserService getUserService() {
        return userService;
    }

    public User getUser() {
        return user;
    }

    public void before(HttpServletRequest request, Map<String, Object> view) {
        Long userId = (Long) request.getSession().getAttribute(USER_ID_SESSION_KEY);
        if (userId != null) {
            user = userService.find(userId);
            view.put("user", user);
        }
    }

    public void after(HttpServletRequest request, Map<String, Object> view) {
        // No operations.
    }
}
