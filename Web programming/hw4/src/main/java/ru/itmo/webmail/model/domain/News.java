package ru.itmo.webmail.model.domain;

import java.io.Serializable;

public class News implements Serializable {
    public long getUserId() {
        return userId;
    }

    public String getText() {
        return text;
    }

    public News(long userId, String text) {
        this.userId = userId;
        this.text = text;
    }

    private long userId;
    private String text;
}
