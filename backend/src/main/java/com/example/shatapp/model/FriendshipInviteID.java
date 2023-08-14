package com.example.shatapp.model;

import java.io.Serializable;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;

@Embeddable
public class FriendshipInviteID implements Serializable {

    private static final long serialVersionUID = 1L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private User from;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private User to;

    public FriendshipInviteID() {

    }

    public FriendshipInviteID(User from, User to) {
        this.from = from;
        this.to = to;
    }

    public User getFrom() {
        return from;
    }

    public User getTo() {
        return to;
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        FriendshipInviteID other = (FriendshipInviteID) obj;
        return Objects.equals(from, other.from) && Objects.equals(to, other.to);
    }

}
