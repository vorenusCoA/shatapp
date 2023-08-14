package com.example.shatapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import com.example.shatapp.model.Message;
import com.example.shatapp.model.MessageDTO;
import com.example.shatapp.model.MessageID;
import com.example.shatapp.model.User;
import com.example.shatapp.service.MessageService;
import com.example.shatapp.service.UserService;

@Controller
public class ChatRoomMessageController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @MessageMapping("/private-messages")
    public void listean(@Payload MessageDTO messageDTO) {

        User fromUser = this.userService.findByEmail(messageDTO.getFrom()).get();
        User toUser = this.userService.findByEmail(messageDTO.getTo()).get();

        Message message = new Message();
        MessageID id = new MessageID(fromUser, toUser);
        message.setId(id);
        message.setContent(messageDTO.getContent());

        this.messageService.save(message);

        this.simpMessagingTemplate.convertAndSendToUser(messageDTO.getTo(), "/messages",
                messageDTO);
    }

}
