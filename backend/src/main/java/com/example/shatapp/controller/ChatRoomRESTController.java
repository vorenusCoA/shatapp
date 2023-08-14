package com.example.shatapp.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.shatapp.model.ChatRoom;
import com.example.shatapp.model.User;
import com.example.shatapp.service.ChatRoomService;
import com.example.shatapp.service.UserService;

@RestController
@RequestMapping(path = "api/room")
@CrossOrigin(origins = "http://localhost:4200")
public class ChatRoomRESTController {

    @Autowired
    private ChatRoomService chatRoomService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ChatRoom createChatRoom(JwtAuthenticationToken principal) {
        ChatRoom chatRoom = new ChatRoom();
        return this.chatRoomService.createChatRoom(chatRoom);
    }

    @GetMapping("/{chatRoomId}/user")
    public ResponseEntity<List<User>> getUsersInRoom(@PathVariable UUID chatRoomId) {
        List<User> users = userService.getUsersInChatRoom(chatRoomId);
        return ResponseEntity.ok(users);
    }
    
    @GetMapping("/{chatRoomId}")
    public ResponseEntity<ChatRoom> findChatRoomById(@PathVariable UUID chatRoomId) {
        Optional<ChatRoom> optChatRoom = this.chatRoomService.findChatRoomById(chatRoomId);
        if (optChatRoom.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(optChatRoom.get());
    }

}
