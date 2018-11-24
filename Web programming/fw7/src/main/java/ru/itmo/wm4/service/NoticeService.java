package ru.itmo.wm4.service;

import org.springframework.stereotype.Service;
import ru.itmo.wm4.domain.Notice;
import ru.itmo.wm4.form.NoticeCredentials;
import ru.itmo.wm4.repository.NoticeRepository;

import java.util.List;

@Service
public class NoticeService {
    private final NoticeRepository noticeRepository;

    public NoticeService(NoticeRepository noticeService) {
        this.noticeRepository = noticeService;
    }

    public List<Notice> findAll() {
        return noticeRepository.findByOrderByCreationTimeDesc();
    }

    public void save(NoticeCredentials noticeForm) {
        Notice notice = new Notice();
        notice.setContent(noticeForm.getContent());
        noticeRepository.save(notice);
    }

    public boolean isContentVacant(String content) {
        return !content.isEmpty();
    }
}
