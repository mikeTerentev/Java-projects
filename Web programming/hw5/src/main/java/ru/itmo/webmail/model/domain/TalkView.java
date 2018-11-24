package ru.itmo.webmail.model.domain;


import java.sql.Timestamp;

public class TalkView {
    private String text;
    private long id;
    private String sourceUserLogin,targetUserLogin;
    private String time;

    public void setTime(Timestamp time) {
        this.time = time.toString();
    }



    public TalkView(String sourceUserLogin, String targetUserLogin, String text, Timestamp time) {
        this.text = text;
        this.time = time.toString();
        this.sourceUserLogin = sourceUserLogin;
        this.targetUserLogin = targetUserLogin;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setSourceUserLogin(String sourceUserLogin) {
        this.sourceUserLogin = sourceUserLogin;
    }

    public void setTargetUserLogin(String targetUserLogin) {
        this.targetUserLogin = targetUserLogin;
    }

    public String getText() {
        return text;
    }

    public long getId() {
        return id;
    }

    public String getSourceUserLogin() {
        return sourceUserLogin;
    }

    public String getTargetUserLogin() {
        return targetUserLogin;
    }
}