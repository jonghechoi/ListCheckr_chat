package com.example.chat.domain.dto;

import lombok.Data;

@Data
public class MessageRequestDto {
    private String type;
    private String boardId;
    private String sender;
    private String message;
    private String time;
}
