package com.signore.hater.Service;

import com.signore.hater.Entity.Message;

import java.util.List;

public interface MessageService {
    List<Message> findAll();
    Message save(Message message);
    List<Message> findByTag(String tag);
}
