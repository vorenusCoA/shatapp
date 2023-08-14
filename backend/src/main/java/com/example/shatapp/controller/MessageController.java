package com.example.shatapp.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.shatapp.model.Message;
import com.example.shatapp.model.MessageDTO;
import com.example.shatapp.model.User;
import com.example.shatapp.service.MessageService;
import com.example.shatapp.service.UserService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path = "/api/message")
public class MessageController {

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @GetMapping
    public ResponseEntity<?> getMessages(JwtAuthenticationToken principal) {

        Optional<User> optUser =
                this.userService.findByEmail(principal.getToken().getClaim("email"));
        if (optUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        User user = optUser.get();
        List<Message> messages = this.messageService.getMessages(user);
        List<MessageDTO> messagesDTO = messages.stream().map(message -> new MessageDTO(message)).collect(Collectors.toList());
        return ResponseEntity.ok(messagesDTO);
    }
}
