package ru.itmo.wm4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itmo.wm4.domain.Comment;
import ru.itmo.wm4.domain.Notice;

import java.util.List;

public interface CommentRepository   extends JpaRepository<Comment, Long> {
    List<Comment> findAll();
}
