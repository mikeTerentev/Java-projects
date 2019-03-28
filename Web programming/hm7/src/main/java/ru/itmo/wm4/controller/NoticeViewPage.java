package ru.itmo.wm4.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ru.itmo.wm4.domain.Comment;
import ru.itmo.wm4.domain.Notice;
import ru.itmo.wm4.domain.Role;
import ru.itmo.wm4.form.validator.UserCredentialsEnterValidator;
import ru.itmo.wm4.security.AnyRole;
import ru.itmo.wm4.security.Guest;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Controller
public class NoticeViewPage extends Page {

    @Guest
    @GetMapping(path = "/notice/{id}")
    @AnyRole({Role.Name.USER, Role.Name.ADMIN})
    public String noticeGet(Model model,
                            @PathVariable("id") long id) {
        Notice notice = getNoticeService().findById(id);
        if (isNull(notice)) {
            model.addAttribute("error", "Notice not found");
            return "NoticeViewPage";
        }
        model.addAttribute("notice", notice);
        model.addAttribute("comment", new Comment());
        return "NoticeViewPage";
    }

    @PostMapping(path = "/notice/{id}")
    @AnyRole({Role.Name.USER, Role.Name.ADMIN})
    public String makeComment(@PathVariable long id,
                              @Valid @ModelAttribute("comment") Comment comment,
                              BindingResult bindingResult, HttpSession httpSession,Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("notice", getNoticeService().findById(id));
            model.addAttribute("comment", new Comment());
            return "NoticeViewPage";
        }
        Notice notice = getNoticeService().findById(id);
        comment.setNotice(notice);
        comment.setUser(getUser(httpSession));
        if (nonNull(notice)) {
            getCommentService().save(comment);
        }
        return "redirect:/notice/" + id;
    }
}