package com.example.chat.domain;

import com.example.chat.domain.dto.MessageRequestDto;
import lombok.Data;

@Data
public class MessageContent {
    private String sender;
    private String content;
    private String sendTime;

    public static MessageContent toMessageContent(MessageRequestDto dto) {
        MessageContent messageContent = new MessageContent();
        messageContent.setSender(dto.getMessageContent().getSender());
        messageContent.setContent(dto.getMessageContent().getContent());
        messageContent.setSendTime(dto.getMessageContent().getSendTime());

        return messageContent;
    }
}
