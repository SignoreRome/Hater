package com.signore.hater.Service.ServiceImpl;

import com.signore.hater.Entity.Message;
import com.signore.hater.Repository.MessageRepository;
import com.signore.hater.Service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("MessageService")
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public List<Message> findAll() {
        return messageRepository.findAll();
    }

    @Override
    @Transactional
    public Message save(Message message) {
        return messageRepository.save(message);
    }

    @Override
    public List<Message> findByTag(String tag) {
        return messageRepository.findByTag(tag);
    }
}
