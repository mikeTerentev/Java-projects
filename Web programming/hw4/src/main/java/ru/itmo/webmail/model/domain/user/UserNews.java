package ru.itmo.webmail.model.domain.user;

public class UserNews {
    public String getLogin() {
        return login;
    }

    public String getText() {
        return text;
    }

    public UserNews(String login, String text) {
        this.login = login;
        this.text = text;
    }

    private String login;
    private String text;
}
