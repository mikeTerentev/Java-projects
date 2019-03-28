package ru.itmo.webmail.web.page;

import ru.itmo.webmail.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import static java.util.Objects.nonNull;

public class LogoutPage extends Page {
    private void action(HttpServletRequest request, Map<String, Object> view) {
        Long userId = (Long) request.getSession().getAttribute(USER_ID_SESSION_KEY);
        if (nonNull(userId)) {
            request.getSession().removeAttribute(USER_ID_SESSION_KEY);
            getUserService().logout(userId);
        }
        throw new RedirectException("/index");
    }
}
