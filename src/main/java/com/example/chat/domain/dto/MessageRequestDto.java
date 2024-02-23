package com.example.chat.domain.dto;

import com.example.chat.domain.MessageContent;
import lombok.Data;

@Data
public class MessageRequestDto {
    private String boardId;
    private String sender;
    private MessageContent message;
    private String time;
    private String type;
}
