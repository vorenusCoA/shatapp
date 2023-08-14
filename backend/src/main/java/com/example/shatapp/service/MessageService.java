package com.example.shatapp.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.example.shatapp.model.Message;
import com.example.shatapp.model.User;
import com.example.shatapp.repository.MessageRepository;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public List<Message> getMessages(User user) {
        return this.messageRepository.findAllMessagesFromAndToUser(user);
    }

    public Message save(Message message) {
        return this.messageRepository.save(message);
    }
}
