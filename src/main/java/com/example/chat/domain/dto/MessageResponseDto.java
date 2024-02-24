package com.example.chat.domain.dto;

import com.example.chat.domain.MessageContent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageResponseDto {
    private String boardId;
    private MessageContent messageContent;
    private String type;
}
