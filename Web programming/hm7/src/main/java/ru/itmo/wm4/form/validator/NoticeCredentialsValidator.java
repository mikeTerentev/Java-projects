package ru.itmo.wm4.form.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.itmo.wm4.form.NoticeCredentials;
import ru.itmo.wm4.service.CommentService;

@Component
public class NoticeCredentialsValidator  implements Validator {
        private final CommentService noticeService;

        public NoticeCredentialsValidator(CommentService noticeService) {
            this.noticeService = noticeService;
        }

        @Override
        public boolean supports(Class<?> clazz) {
            return NoticeCredentials.class.equals(clazz);
        }

        @Override
        public void validate(Object target, Errors errors) {
            if (!errors.hasErrors()) {
                NoticeCredentials form = (NoticeCredentials) target;
                String tags = form.getTags().trim();
                if (!tags.matches("[a-zA-Z\\p{javaWhitespace}]*")) {
                    errors.rejectValue("tags", "tags.invalid", "Tags should contain only letters and digits, separated with whitespaces");
                }
            }
        }
}
