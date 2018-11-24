package ru.itmo.wm4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.itmo.wm4.domain.Notice;
import ru.itmo.wm4.domain.User;

import java.util.List;


public interface NoticeRepository extends JpaRepository<Notice, Long> {

    @Query(value = "SELECT * FROM notice WHERE login=?1", nativeQuery = true)
    List<Notice> findByLogin(String login);
    List<Notice> findByOrderByCreationTimeDesc();
}
