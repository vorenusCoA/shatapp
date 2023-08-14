package com.example.shatapp.model;

import java.sql.Timestamp;

public class MessageDTO {

    private String from;
    private String to;
    private String content;
    private Timestamp sentDateTime;

    public MessageDTO() {}

    public MessageDTO(Message message) {
        this.from = message.getId().getFromUser().getEmail();
        this.to = message.getId().getToUser().getEmail();
        this.content = message.getContent();
        this.sentDateTime = message.getSentDatetime();
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getContent() {
        return content;
    }

    public Timestamp getSentDateTime() {
        return sentDateTime;
    }

}
