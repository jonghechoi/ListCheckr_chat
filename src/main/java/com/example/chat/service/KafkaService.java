package com.example.chat.service;

import com.example.chat.domain.dto.MessageRequestDto;
import com.example.chat.domain.dto.MessageResponseDto;
import com.example.chat.repository.MongoRepository;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaService {
    private final SimpMessageSendingOperations sendingOperations;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final MongoRepository mongoRepository;

    public void send(MessageRequestDto messageRequestDto) throws JsonProcessingException{
        log.info("Kafka Publish Message : {}", messageRequestDto.getMessage());

        String message = objectMapper.writeValueAsString(messageRequestDto);
        kafkaTemplate.send("topic_chat", message);
    }

    @KafkaListener(topics = "topic_chat")
    public void consume(String subscribeString) {
        log.info("Kafka Consume Message : {}", subscribeString);

        try {
            MessageResponseDto messageResponseDto = objectMapper.readValue(subscribeString, MessageResponseDto.class);
            sendingOperations.convertAndSend("/topic/aaa", messageResponseDto);

            mongoRepository.createAndSaveChatListByBoardId(messageResponseDto);
        } catch(JsonProcessingException e) {
            log.error("[Kafka Consume] Json Parse Excetpion Occured : {}", e.getMessage());
        }
    }
}
