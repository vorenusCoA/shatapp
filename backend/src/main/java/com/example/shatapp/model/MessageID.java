package com.example.shatapp.model;

import java.io.Serializable;
import java.util.Objects;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;

@Embeddable
public class MessageID implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @ManyToOne(fetch = FetchType.LAZY)
    private User fromUser;

    @ManyToOne(fetch = FetchType.LAZY)
    private User toUser;

    public MessageID() {}
    
    public MessageID(User fromUser, User toUser) {
        this.fromUser = fromUser;
        this.toUser = toUser;
    }



    public User getFromUser() {
        return fromUser;
    }

    public User getToUser() {
        return toUser;
    }

    @Override
    public int hashCode() {
        return Objects.hash(fromUser, toUser);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MessageID other = (MessageID) obj;
        return Objects.equals(fromUser, other.fromUser) && Objects.equals(toUser, other.toUser);
    }

}
