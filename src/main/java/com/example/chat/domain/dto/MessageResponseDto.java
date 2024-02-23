package com.example.chat.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageResponseDto {
    private String boardId;
    private String sender;
    private String message;
    private String time;
    private String type;
}
