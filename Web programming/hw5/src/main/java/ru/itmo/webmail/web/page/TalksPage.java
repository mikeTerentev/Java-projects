package ru.itmo.webmail.web.page;

import ru.itmo.webmail.model.domain.Talk;
import ru.itmo.webmail.model.domain.TalkView;
import ru.itmo.webmail.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

public class TalksPage extends Page {
    private void action(HttpServletRequest request, Map<String, Object> view) {
        if (isNull(request.getSession().getAttribute(USER_ID_SESSION_KEY))) {
            throw new RedirectException("/enter");
        }
        view.put("talks", getTalkViews());
    }

    private List<TalkView> getTalkViews() {
        return getTalkService().findAll(getUser().getId())
                .stream()
                .map(this::toView)
                .collect(Collectors.toList());
    }
    private TalkView toView(Talk talk) {
        String sourceUserLogin = getUserService().find(talk.getSourceUserId()).getLogin();
        String targetUserLogin = getUserService().find(talk.getTargetUserId()).getLogin();
       TalkView x= new TalkView(sourceUserLogin, targetUserLogin, talk.getText(),talk.getCreationTime());
        return x;
    }
    public void addTalk(HttpServletRequest request, Map<String, Object> view) {
        if(isNull(getUser()))  throw new RedirectException("/enter");
        long sourceUserId = (long) request.getSession().getAttribute(USER_ID_SESSION_KEY);
        String targetUserLogin = request.getParameter("targetUserLogin");
        String text = request.getParameter("text");
        try {
           // getTalkService().validateTalk(targetUserLogin, text);
            getTalkService().addTalk(sourceUserId, targetUserLogin, text);
        } catch (Exception e) {
            view.put("error", e.getMessage());
            view.put("targetUserLogin", targetUserLogin);
            view.put("text", text);
            view.put("talks", getTalkViews());
            return;
        }
       throw new RedirectException("/talks");
    }
}
