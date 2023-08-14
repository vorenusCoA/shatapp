package com.example.shatapp.model;

public class ChatMessage {

    private ChatMessageType type;
    private String content;
    private UserDTO sender;
    private String receiverEmail;

    public ChatMessage() {}

    public ChatMessage(ChatMessageType type, String content, UserDTO sender, String receiverEmail) {
        this.type = type;
        this.content = content;
        this.sender = sender;
        this.receiverEmail = receiverEmail;
    }

    public ChatMessageType getType() {
        return type;
    }

    public String getContent() {
        return content;
    }

    public UserDTO getSender() {
        return sender;
    }

    public String getReceiverEmail() {
        return receiverEmail;
    }

}
