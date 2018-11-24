package ru.itmo.webmail.model.domain;

import java.util.Date;

public class ArticleView {

    private long id;
    private String title;
    private String text;
    private boolean hidden;
    private Date creationTime;
    private String login;

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }


    public void setId(long id) {
        this.id = id;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }


    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public Date getCreationTime() {
        return creationTime;
    }


}
