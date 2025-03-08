package com.example.CS_1.service;

import com.example.CS_1.dao.MessageRepository;
import com.example.CS_1.entity.Message;
import com.example.CS_1.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Message addMessage(String content, User user) {
        Message message = new Message();
        message.setText(content);
        message.setUser(user);
        return messageRepository.save(message);
    }

    public void deleteMessage(Long id, User user) {
        messageRepository.deleteById(id);
    }

    public List<Message> findAll() {
        System.out.println("messageRepository.findAll(): " + messageRepository.findAll());
        return messageRepository.findAll();
    }

    public void save(Message message) {
        messageRepository.save(message);
    }

    public Message findById(Long id) {
        return messageRepository.findById(id).get();
    }

    public void delete(Message message) {
        messageRepository.delete(message);
    }
}
