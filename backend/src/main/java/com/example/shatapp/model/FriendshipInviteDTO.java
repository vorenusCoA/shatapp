package com.example.shatapp.model;

import java.time.LocalDateTime;

public class FriendshipInviteDTO {

    private String from;
    private String to;
    private InviteStatus status;
    private LocalDateTime sentDateTime;
    private String fromPicture;
    private String toPicture;
    
    public FriendshipInviteDTO() {}
    
    public FriendshipInviteDTO(FriendshipInvite invite) {
        this.from = invite.getId().getFrom().getEmail();
        this.to = invite.getId().getTo().getEmail();
        this.status = invite.getStatus();
        this.sentDateTime = invite.getSentDateTime();
        this.fromPicture = invite.getId().getFrom().getPicture();
        this.toPicture = invite.getId().getTo().getPicture();
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public InviteStatus getStatus() {
        return status;
    }

    public LocalDateTime getSentDateTime() {
        return sentDateTime;
    }

    public String getFromPicture() {
        return fromPicture;
    }
    
    public String getToPicture() {
        return toPicture;
    }

}
