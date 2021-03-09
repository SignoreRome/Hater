package com.signore.hater.Service;

import com.signore.hater.Entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MessageService {
    Page<Message> findAll(Pageable pageable);
    Message save(Message message);
    Page<Message> findByTag(String tag, Pageable pageable);
    List<Message> findAll();
    List<Message> findByTag(String tag);
}
