package ru.itmo.webmail.model.service;

import ru.itmo.webmail.model.domain.Talk;
import ru.itmo.webmail.model.repository.TalkRepository;
import ru.itmo.webmail.model.repository.UserRepository;
import ru.itmo.webmail.model.repository.impl.TalkRepositoryImpl;
import ru.itmo.webmail.model.repository.impl.UserRepositoryImpl;

import java.util.Arrays;
import java.util.List;

public class TalkService {
    private UserRepository userRepository = new UserRepositoryImpl();
    private TalkRepository talkRepository = new TalkRepositoryImpl();
   public void addTalk(long sourceUserId, String targetUserLogin, String text){
       talkRepository.addTalk(sourceUserId,userRepository.findByLogin(targetUserLogin).getId(),text);
   }

    public List<Talk> findAll(long id) {
     return   talkRepository.findAll(id);
    }
}
