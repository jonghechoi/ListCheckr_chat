package com.example.chat.domain;

import com.example.chat.domain.dto.MessageRequestDto;

public class Message {
    private String boardId;
    private String sender;
    private String message;
    private String time;

    public Message(MessageRequestDto message) {
        this.boardId = message.getBoardId();
        this.sender = message.getSender();
        this.message = message.getMessage();
        this.time = message.getTime();
    }
}
