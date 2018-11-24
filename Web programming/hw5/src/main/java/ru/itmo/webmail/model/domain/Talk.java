package ru.itmo.webmail.model.domain;

import javax.xml.crypto.Data;
import java.sql.Date;
import java.sql.Timestamp;

public class Talk {
    private String text;
    private long id;
    private long sourceUserId,targetUserId;
    private Timestamp creationTime;

    public Timestamp getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Timestamp creationTime) {
        this.creationTime = creationTime;
    }


    public void setText(String text) {
        this.text = text;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setSourceUserId(long sourceUserId) {
        this.sourceUserId = sourceUserId;
    }

    public void setTargetUserId(long targetUserId) {
        this.targetUserId = targetUserId;
    }

    public long getId() {
        return id;
    }

    public long getSourceUserId() {
        return sourceUserId;
    }

    public long getTargetUserId() {
        return targetUserId;
    }

    public String getText() {
        return text;
    }

}
