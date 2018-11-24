package ru.itmo.webmail.web.page;

import ru.itmo.webmail.model.exception.RepositoryException;
import ru.itmo.webmail.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class ConfirmPage extends Page {
    private void action(HttpServletRequest request, Map<String, Object> view) {
        String secret = request.getParameter("secret");
        int updated = 0;
        try {
            updated = getUserService().confirm(secret);
        } catch (RepositoryException ignored) {

        }
        if (updated == 0) {
            throw new RedirectException("/index", "confirmationError");
        }
        throw new RedirectException("/index", "confirmationDone");
    }
}
