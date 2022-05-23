package com.example.socialnetwork;

public class Message {
    String message = null;
    String senderId = null;

    Message() {
    }

    Message(String message, String senderId) {
        this.message = message;
        this.senderId = senderId;
    }

    public String getMessage() {
        return message;
    }

    public String getSenderId() {
        return senderId;
    }
}
