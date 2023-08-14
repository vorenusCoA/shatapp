package com.example.shatapp.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.shatapp.model.FriendshipInvite;
import com.example.shatapp.model.FriendshipInviteDTO;
import com.example.shatapp.model.FriendshipInviteID;
import com.example.shatapp.model.InviteStatus;
import com.example.shatapp.model.User;
import com.example.shatapp.service.FriendshipInviteService;
import com.example.shatapp.service.UserService;

@RestController
@RequestMapping(path = "/api/friendship-invite")
@CrossOrigin(origins = "http://localhost:4200")
public class FriendshipInviteController {

    @Autowired
    private FriendshipInviteService frienshipInviteService;

    @Autowired
    private UserService userService;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @GetMapping()
    public ResponseEntity<?> getFriendShipInvites(JwtAuthenticationToken principal) {

        String userEmail = principal.getToken().getClaim("email");
        User user = this.userService.findByEmailAndFetchFriendshipInvites(userEmail).get();

        List<FriendshipInviteDTO> invites = user.getInvitesReceived().stream()
                .map(invite -> new FriendshipInviteDTO(invite)).collect(Collectors.toList());

        return ResponseEntity.ok(invites);
    }

    @PostMapping()
    public ResponseEntity<?> inviteFriend(@RequestBody String friendEmail,
            JwtAuthenticationToken principal) {

        String userEmail = principal.getToken().getClaim("email");
        User user = this.userService.findByEmail(userEmail).get();
        Optional<User> optFriend = this.userService.findByEmail(friendEmail);

        if (optFriend.isEmpty()) {
            return ResponseEntity.badRequest().body("Destination user does not exist");
        }
        User friend = optFriend.get();

        FriendshipInviteID idFromTo = new FriendshipInviteID(user, friend);
        FriendshipInviteID idToFrom = new FriendshipInviteID(friend, user);
        Optional<FriendshipInvite> optInviteFromTo = this.frienshipInviteService.findById(idFromTo);
        Optional<FriendshipInvite> optInviteToFrom = this.frienshipInviteService.findById(idToFrom);

        if (optInviteFromTo.isEmpty() && optInviteToFrom.isEmpty()) {

            System.out.println("CREATING INVITE");
            FriendshipInvite invite =
                    new FriendshipInvite(user, friend, InviteStatus.PENDING, LocalDateTime.now());
            this.frienshipInviteService.save(invite);

            FriendshipInviteDTO inviteDTO = new FriendshipInviteDTO(invite);

            this.simpMessagingTemplate.convertAndSendToUser(friend.getEmail(), "/invites",
                    inviteDTO);
            return ResponseEntity.ok(invite);
        }

        return ResponseEntity.status(HttpStatus.CONFLICT).body("The invite already exists");
    }

    @PutMapping
    public ResponseEntity<?> respondInvite(@RequestBody FriendshipInviteDTO response) {

        User from = this.userService.findByEmail(response.getFrom()).get();
        User to = this.userService.findByEmail(response.getTo()).get();

        Optional<FriendshipInvite> optInvite = this.frienshipInviteService.findById(from, to);
        if (optInvite.isEmpty()) {
            return ResponseEntity.badRequest().body("Invitation does not exist");
        }

        FriendshipInvite invite = this.frienshipInviteService
                .processInviteNewStatus(optInvite.get(), response.getStatus());

        return ResponseEntity.ok(invite);
    }
}
