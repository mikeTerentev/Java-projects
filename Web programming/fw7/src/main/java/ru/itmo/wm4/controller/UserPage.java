package ru.itmo.wm4.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.itmo.wm4.service.UserService;

@Controller
public class UserPage extends  Page {
    private final UserService userService;

    public UserPage(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/users/{id}")
    public String main(@PathVariable(value = "id") long id, Model model) {
        model.addAttribute("user",userService.findById(id));
        return "UserPage";
    }
}
