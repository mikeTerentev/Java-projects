package ru.itmo.webmail.web.page;

import ru.itmo.webmail.model.domain.User;
import ru.itmo.webmail.model.exception.RepositoryException;
import ru.itmo.webmail.model.exception.ValidationException;
import ru.itmo.webmail.model.service.UserService;
import ru.itmo.webmail.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AdminPage extends Page {
    @Override
    public void before(HttpServletRequest request, Map<String, Object> view) {
        super.before(request, view);

        if (getUser() == null) {
            throw new RedirectException("/enter");
        }
        if (!getUser().isAdmin()) {
            throw new RedirectException("/index");
        }
    }

    private Map<String, Object> change(HttpServletRequest request, Map<String, Object> view) {
        String userId = request.getParameter("id");
        ///???
        if (getUser().isAdmin()) {
            boolean isAdmin = getUserService().change(userId);
            view.put("success", true);
            view.put("admin", isAdmin);
        }else {
            view.put("success", false);
            view.put("error","You are not an admin");
        }
        return view;
    }

    private List<User> find(HttpServletRequest request, Map<String, Object> view) {
        return getUserService().findAll();
    }

    private void action(HttpServletRequest request, Map<String, Object> view) {
        // No operations.
    }
}
