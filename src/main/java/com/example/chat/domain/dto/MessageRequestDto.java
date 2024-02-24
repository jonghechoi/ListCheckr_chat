package com.example.chat.domain.dto;

import com.example.chat.domain.MessageContent;
import lombok.Data;

@Data
public class MessageRequestDto {
    private String boardId;
    private MessageContent messageContent;
    private String type;
}
