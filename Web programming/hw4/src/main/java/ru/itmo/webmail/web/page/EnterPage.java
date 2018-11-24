package ru.itmo.webmail.web.page;

import ru.itmo.webmail.model.domain.User;
import ru.itmo.webmail.model.exception.ValidationException;
import ru.itmo.webmail.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class EnterPage extends Page {

    private void action() {
        // nothing
    }

    private void enter(HttpServletRequest request, Map<String, Object> view) {
        String login = request.getParameter("handleOrEmail");
        String password = request.getParameter("password");
        User user;
        try {
            user = userService.login(login, password);
        } catch (ValidationException e) {
            view.put("error", e.getMessage());
            view.put("login", login);
            return;
        }
        request.getSession().setAttribute("user", user);
        throw new RedirectException("/index");
    }
}