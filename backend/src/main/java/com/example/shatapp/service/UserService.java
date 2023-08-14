package com.example.shatapp.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;
import com.example.shatapp.model.User;
import com.example.shatapp.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User save(User user) {
        return this.userRepository.save(user);
    }

    public Optional<User> findByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }
    
    public Optional<User> findByEmailAndFetchChatRooms(String email) {
        return this.userRepository.findByEmailAndFetchChatRooms(email);
    }

    public List<User> getUsersInChatRoom(UUID chatRoomId) {
        return this.userRepository.findUsersInChatRoom(chatRoomId);
    }

    public Optional<User> getUserByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    public Optional<User> findByEmailAndFetchFriends(String email) {
        return this.userRepository.findByEmailAndFetchFriends(email);
    }

    public Optional<User> findByEmailAndFetchFriendshipInvites(String userEmail) {
        return this.userRepository.findByEmailAndFetchFriendshipInvites(userEmail);
    }

}
