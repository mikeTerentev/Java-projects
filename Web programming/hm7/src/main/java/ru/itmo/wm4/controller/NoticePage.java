package ru.itmo.wm4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.wm4.domain.Role;
import ru.itmo.wm4.form.NoticeCredentials;
import ru.itmo.wm4.form.validator.NoticeCredentialsValidator;
import ru.itmo.wm4.security.AnyRole;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class NoticePage extends Page {
    private final NoticeCredentialsValidator validator;

    @Autowired
    public NoticePage(NoticeCredentialsValidator validator) {
        this.validator = validator;
    }


    @AnyRole(Role.Name.ADMIN)
    @GetMapping(path = "/notice")
    public String noticeGet(Model model) {
        model.addAttribute("notice", new NoticeCredentials());
        return "NoticePage";
    }

    @AnyRole(Role.Name.ADMIN)
    @PostMapping(path = "/notice")
    public String noticePost(@Valid @ModelAttribute("notice") NoticeCredentials noticeForm,
                               BindingResult bindingResult, HttpSession httpSession) {
        if (bindingResult.hasErrors()) {
            return "NoticePage";
        }

        getNoticeService().save(noticeForm.getText(),noticeForm.getTags() ,getUser(httpSession));
        return "redirect:/notices";
    }

    private Set<String> getTags(NoticeCredentials notice) {
        return  Arrays.stream(notice.getTags().split("\\p{javaWhitespace}")).filter(x->!x.isEmpty()).collect(Collectors.toSet());
    }
}
