package com.example.shatapp.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import com.example.shatapp.model.FriendshipInvite;
import com.example.shatapp.model.FriendshipInviteDTO;
import com.example.shatapp.model.FriendshipInviteID;
import com.example.shatapp.model.InviteStatus;
import com.example.shatapp.model.User;
import com.example.shatapp.repository.FriendshipInviteRepository;
import com.example.shatapp.repository.UserRepository;

@Service
public class FriendshipInviteService {

    private final FriendshipInviteRepository friendshipInviteRepository;

    private final UserRepository userRepository;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    public FriendshipInviteService(FriendshipInviteRepository friendshipInviteRepository,
            UserRepository userRepository) {
        this.friendshipInviteRepository = friendshipInviteRepository;
        this.userRepository = userRepository;
    }

    public Optional<FriendshipInvite> findById(User from, User to) {
        FriendshipInviteID id = new FriendshipInviteID(from, to);
        return this.friendshipInviteRepository.findById(id);
    }

    public Optional<FriendshipInvite> findById(FriendshipInviteID id) {
        return this.friendshipInviteRepository.findById(id);
    }

    public FriendshipInvite save(FriendshipInvite invite) {
        return this.friendshipInviteRepository.save(invite);
    }

    public FriendshipInvite processInviteNewStatus(FriendshipInvite friendshipInvite,
            InviteStatus status) {

        friendshipInvite.setStatus(status);
        this.friendshipInviteRepository.save(friendshipInvite);

        if (status.equals(InviteStatus.ACCEPTED)) {
            // create a friendship
            friendshipInvite.getId().getFrom().addFriend(friendshipInvite.getId().getTo());
            this.userRepository.save(friendshipInvite.getId().getFrom());

            FriendshipInviteDTO inviteDTO = new FriendshipInviteDTO(friendshipInvite);
            this.simpMessagingTemplate.convertAndSendToUser(
                    friendshipInvite.getId().getFrom().getEmail(), "/invites", inviteDTO);

        }

        return friendshipInvite;
    }

}
