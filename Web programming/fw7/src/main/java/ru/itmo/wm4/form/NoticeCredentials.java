package ru.itmo.wm4.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class NoticeCredentials {

    @NotEmpty
    @Size(min = 1)
    private String content;


    public void setContent(String content) {
        this.content = content;
    }


    public String getContent() {
       return content;
    }

}
