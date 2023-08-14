package com.example.shatapp.service;

import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;
import com.example.shatapp.model.ChatRoom;
import com.example.shatapp.model.User;
import com.example.shatapp.repository.ChatRoomRepository;

@Service
public class ChatRoomService {

    private ChatRoomRepository chatRoomRepository;

    public ChatRoomService(ChatRoomRepository chatRoomRepository) {
        this.chatRoomRepository = chatRoomRepository;
    }

    public ChatRoom createChatRoom(ChatRoom chatRoom) {
        return this.chatRoomRepository.save(chatRoom);
    }

    public void addUserToChatRoom(String chatRoomId, User user) {

        UUID id = UUID.fromString(chatRoomId);
        ChatRoom chatRoom = this.chatRoomRepository.findByIdAndFetchUsers(id).get();
        chatRoom.addUser(user);
        this.chatRoomRepository.save(chatRoom);

    }

    public void removeUserFromChatRoom(String chatRoomId, User user) {
        UUID id = UUID.fromString(chatRoomId);
        ChatRoom chatRoom = this.chatRoomRepository.findByIdAndFetchUsers(id).get();
        chatRoom.removeUser(user);
        this.chatRoomRepository.save(chatRoom);
    }

    public Optional<ChatRoom> findChatRoomById(UUID chatRoomId) {
        return this.chatRoomRepository.findById(chatRoomId);
    }

}
