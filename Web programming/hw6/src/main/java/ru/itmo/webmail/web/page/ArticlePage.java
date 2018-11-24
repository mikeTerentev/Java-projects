package ru.itmo.webmail.web.page;

import ru.itmo.webmail.model.domain.Article;
import ru.itmo.webmail.model.domain.ArticleView;
import ru.itmo.webmail.model.domain.User;
import ru.itmo.webmail.model.exception.ValidationException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ArticlePage extends Page {
    private Map<String, Object> article(HttpServletRequest request, Map<String, Object> view) {
        Article article = new Article();
        long userId = (long) request.getSession().getAttribute(USER_ID_SESSION_KEY);
        article.setUserId(userId);
        article.setText(request.getParameter("line"));
        article.setTitle(request.getParameter("title"));
        try {
            getArticleService().validateArticle(article, userId);
        } catch (ValidationException e) {
            view.put("success", false);
            view.put("error", e.getMessage());
            return view;
        }

        getArticleService().addAtricle(article, userId);
        view.put("success", true);

        return view;
    }

    private List<ArticleView> toPublicView(HttpServletRequest request, Map<String, Object> view) {
        return getArticleService().findPublic().stream()
                .map(this::toArticleView)
                .collect(Collectors.toList());
    }

    private ArticleView toArticleView(Article article) {
        ArticleView x = new ArticleView();
        x.setLogin(getUserService().find(article.getUserId()).getLogin());
        x.setCreationTime(article.getCreationTime());
        x.setText(article.getText());
        x.setTitle(article.getTitle());
        return x;
    }

    private void action(HttpServletRequest request, Map<String, Object> view) {
        // No operations.
    }
}
