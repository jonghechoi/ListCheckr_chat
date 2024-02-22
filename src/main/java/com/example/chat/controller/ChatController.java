package com.example.chat.controller;

import com.example.chat.domain.dto.MessageRequestDto;
import com.example.chat.service.KafkaService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatController {
    private final KafkaService kafkaService;

    @MessageMapping("/topic/{boardId}")
    public void chat(@DestinationVariable String boardId, MessageRequestDto messageRequestDto) {
        log.info("board : {}", boardId);

        // kafka
        try {
            kafkaService.send(messageRequestDto);
        } catch(JsonProcessingException e) {
            log.error("[Kafka Send] Json Parse Excetpion Occured : {}", e.getMessage());
        }
    }
}
