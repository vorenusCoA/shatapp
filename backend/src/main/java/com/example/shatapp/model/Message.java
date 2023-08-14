package com.example.shatapp.model;

import java.sql.Timestamp;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;

@Entity
public class Message {

    @EmbeddedId
    private MessageID id;

    private String content;

    private Timestamp sentDatetime;

    public MessageID getId() {
        return id;
    }

    public void setId(MessageID id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getSentDatetime() {
        return sentDatetime;
    }

    public void setSentDatetime(Timestamp sentDatetime) {
        this.sentDatetime = sentDatetime;
    }

}
