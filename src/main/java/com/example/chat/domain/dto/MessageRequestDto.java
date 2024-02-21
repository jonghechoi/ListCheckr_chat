package com.example.chat.domain.dto;

import lombok.Data;

@Data
public class MessageRequestDto {

    public enum MessageType {
        ENTER, TALK, LEAVE
    }
    private MessageType type;
    private String boardId;
    private String sender;
    private String message;
    private String time;
}
