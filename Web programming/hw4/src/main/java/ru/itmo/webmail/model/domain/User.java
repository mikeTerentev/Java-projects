package ru.itmo.webmail.model.domain;

import java.io.Serializable;

public class User implements Serializable {
    private String login;
    private  long id;
    private String passwordSha1;
    private String email;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPasswordSha1() {
        return passwordSha1;
    }
    public String getEmail() {
        return email;
    }
    public long getId(){
        return id;
    }

    public void setPasswordSha1(String passwordSha1) {
        this.passwordSha1 = passwordSha1;
    }
    public void setId(long id) {
        this.id = id;
    }

    public void setEmail(String emailSha1) {
        this.email = emailSha1;
    }
}
