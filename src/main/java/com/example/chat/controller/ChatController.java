package com.example.chat.controller;

import com.example.chat.domain.MessageDocument;
import com.example.chat.domain.dto.MessageRequestDto;
import com.example.chat.service.kafka.KafkaService;
import com.example.chat.service.mongo.MongoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatController {
    private final KafkaService kafkaService;
    private final MongoService mongoService;

    @MessageMapping("/topic/{boardId}")
    public void chat(@DestinationVariable String boardId, MessageRequestDto messageRequestDto) {
        try {
            kafkaService.publish(messageRequestDto);
        } catch(JsonProcessingException e) {
            log.error("[Kafka Send] Json Parse Excetpion Occured : {}", e.getMessage());
        }
    }

    @GetMapping("/api/topic/{boardId}")
    public ResponseEntity<MessageDocument> chatHistory(@PathVariable String boardId) {
        MessageDocument messageDocument = mongoService.getChatListByBoardId(boardId);
        return new ResponseEntity<>(messageDocument, HttpStatus.OK);
    }
}