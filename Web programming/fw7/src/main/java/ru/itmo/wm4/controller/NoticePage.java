package ru.itmo.wm4.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ru.itmo.wm4.form.NoticeCredentials;

import ru.itmo.wm4.service.NoticeService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class NoticePage extends Page  {
    private final NoticeService noticeService;


    public  NoticePage( NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @GetMapping(path = "/notice")
    public String noticeGet(Model model,HttpSession httpSession) {
        if(getUser(httpSession) == null){
            return "redirect:/";
        }
        model.addAttribute("noticeForm", new NoticeCredentials());
        return "NoticePage";
    }

    @PostMapping(path = "/notice")
    public String noticePost(@Valid @ModelAttribute("noticeForm") NoticeCredentials noticeForm,
                               BindingResult bindingResult, HttpSession httpSession) {
        if (bindingResult.hasErrors()) {
            return "NoticePage";
        }
        noticeService.save(noticeForm);
        return "redirect:/";
    }
}
