package com.example.shatapp.model;

import java.time.LocalDateTime;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;

@Entity
public class FriendshipInvite {

    @EmbeddedId
    private FriendshipInviteID id;

    private InviteStatus status;
    private LocalDateTime sentDateTime;

    public FriendshipInvite() {}

    public FriendshipInvite(User to, User from, InviteStatus status, LocalDateTime sentDateTime) {
        FriendshipInviteID id = new FriendshipInviteID(to, from);
        this.id = id;
        this.status = status;
        this.sentDateTime = sentDateTime;
    }

    public FriendshipInviteID getId() {
        return id;
    }

    public InviteStatus getStatus() {
        return status;
    }



    public void setStatus(InviteStatus status) {
        this.status = status;
    }

    public LocalDateTime getSentDateTime() {
        return sentDateTime;
    }



}
