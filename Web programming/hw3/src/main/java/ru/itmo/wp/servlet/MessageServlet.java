package ru.itmo.wp.servlet;

import com.google.gson.Gson;
import org.json.JSONPropertyName;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MessageServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String uri = request.getPathInfo();
        response.setContentType("application/json");
        switch (uri) {
            case "/auth":
                auth(request, response);
                break;
            case "/findAll":
                findAll(request, response);
                break;
            case "/add":
                add(request, response);
                break;
            default:
                response.sendError((HttpServletResponse.SC_NOT_FOUND));
                break;
        }
    }

    private void add(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String text = request.getParameter("text");
        String curUser = (String) request.getSession().getAttribute("user");
        messages.add(new Message(curUser, text));
        writeJson(response, json.toJson(new Message(curUser,text)));
    }

    private void findAll(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String answer = json.toJson(messages);
        writeJson(response, answer);
    }

    private void auth(HttpServletRequest request, HttpServletResponse response) throws IOException {
         String curUser = Optional.ofNullable(request.getParameter("user")).orElse("");
         request.getSession(true).setAttribute("user", curUser);
        writeJson(response, json.toJson(curUser));
    }

    private void writeJson(HttpServletResponse response, String mes) throws IOException {
        response.getWriter().print(mes);
        response.getWriter().flush();
    }


    private List<Message> messages = new ArrayList<>();
    private Gson json = new Gson();

    private class Message {
        String user;
        String text;

        Message(String name, String text) {
            this.user = name;
            this.text = text;
        }
    }

}
