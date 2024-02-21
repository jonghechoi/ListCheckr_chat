package com.example.chat.controller;

import com.example.chat.domain.dto.MessageRequestDto;
import com.example.chat.service.KafkaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final KafkaService kafkaService;

    @MessageMapping("/topic/{boardId}")
    public void chat(@DestinationVariable String boardId, MessageRequestDto messageRequestDto) throws JsonProcessingException {
        System.out.println("컨트롤러 들어옴");
        System.out.println("boardId : " + boardId);

        // kafka
        kafkaService.send(messageRequestDto);
//        kafkaService.publish(boardId, messageRequestDto);

        // db
    }
}
