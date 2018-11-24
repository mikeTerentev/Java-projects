package ru.itmo.webmail.web.page;

import ru.itmo.webmail.model.domain.Article;
import ru.itmo.webmail.model.domain.ArticleView;
import ru.itmo.webmail.model.exception.ValidationException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MyArticlePage extends Page {
    private void action(HttpServletRequest request, Map<String, Object> view) {

    }

    private List<ArticleView> toPrivateView(HttpServletRequest request, Map<String, Object> view) {
        List<ArticleView> x= getArticleService().findPrivate(getUser().getId()).stream()
                .map(this::toArticleView)
                .collect(Collectors.toList());
        return x;
    }

    private Map<String, Object> change(HttpServletRequest request, Map<String, Object> view) {
        String articleId = request.getParameter("id");
      ///???
        long id = Long.valueOf(articleId);
        boolean isHidden = getArticleService().changeMod(getUser(), id);
        view.put("success", true);
        view.put("hidden", isHidden);
        return view;
    }


    private ArticleView toArticleView(Article article) {
        ArticleView x = new ArticleView();
        x.setLogin(getUserService().find(article.getUserId()).getLogin());
        x.setCreationTime(article.getCreationTime());
        x.setText(article.getText());
        x.setTitle(article.getTitle());
        x.setId(article.getId());
        x.setHidden(article.isHidden());
        return x;
    }

    private void registrationDone(HttpServletRequest request, Map<String, Object> view) {
        view.put("message", "You have been registered");
    }


}
