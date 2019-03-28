package ru.itmo.wm4.service;

import org.springframework.stereotype.Service;
import ru.itmo.wm4.domain.Notice;
import ru.itmo.wm4.domain.Tag;
import ru.itmo.wm4.domain.User;
import ru.itmo.wm4.repository.NoticeRepository;
import ru.itmo.wm4.repository.TagRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class NoticeService {
    private final NoticeRepository noticeRepository;
   private  final TagRepository tagRepository;
    public NoticeService(NoticeRepository noticeRepository, TagRepository tagRepository) {
        this.noticeRepository = noticeRepository;
        this.tagRepository = tagRepository;
    }

    public List<Notice> findByOrderByCreationTimeDesc() {
        return noticeRepository.findByOrderByCreationTimeDesc();
    }



    public void save(String text, String tagsline, User user) {
        Set<String> tags = Arrays.stream(tagsline.split("\\p{javaWhitespace}"))
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toSet());
        Notice notice= new Notice();
        notice.setText(text);
        user.addNotice(notice);
        notice.setUser(user);
        notice.setTags(tags.stream().map(name -> {
            Tag currentTag=tagRepository.findByName(name).orElseGet(Tag::new);
            currentTag.setName(name);
            return currentTag;
        }).collect(Collectors.toSet()));
        noticeRepository.save(notice);
    }

    public Notice findById(long id) {
       return noticeRepository.findById(id);
    }
}
