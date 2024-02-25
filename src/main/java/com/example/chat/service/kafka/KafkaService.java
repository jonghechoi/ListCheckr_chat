package com.example.chat.service.kafka;

import com.example.chat.domain.MessageDocument;
import com.example.chat.domain.dto.MessageRequestDto;
import com.example.chat.domain.dto.MessageResponseDto;
import com.example.chat.handler.StompHandler;
import com.example.chat.service.mongo.MongoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaService {
    private final SimpMessageSendingOperations sendingOperations;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final MongoService mongoService;
    private final StompHandler stompHandler;

    public void publish(MessageRequestDto messageRequestDto) throws JsonProcessingException{
        log.info("Kafka Publish Message : {}", messageRequestDto.getMessageContent().getContent());

        // Topic 발행
        String message = objectMapper.writeValueAsString(messageRequestDto);
        kafkaTemplate.send("topic_chat", message);

        // DB 저장
        mongoService.appendChatListMapByBoardId(messageRequestDto);
    }

    @KafkaListener(topics = "topic_chat")
    public void consume(String subscribeString) {
        log.info("Kafka Consume Message : {}", subscribeString);

        try {
            // Topic 구독자에게 브로드캐스팅
            MessageResponseDto messageResponseDto = objectMapper.readValue(subscribeString, MessageResponseDto.class);
            sendingOperations.convertAndSend("/topic/" + messageResponseDto.getBoardId(), messageResponseDto);
        } catch(JsonProcessingException e) {
            log.error("[Kafka Consume] Json Parse Excetpion Occured : {}", e.getMessage());
        }
    }
}