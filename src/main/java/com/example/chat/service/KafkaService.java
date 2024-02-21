package com.example.chat.service;

import com.example.chat.domain.Message;
import com.example.chat.domain.dto.MessageRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
//@RequiredArgsConstructor
public class KafkaService {
    private SimpMessageSendingOperations sendingOperations;
    private SimpMessagingTemplate template;
//    private final ChatKafkaProducer chatKafkaProducer;

    private KafkaTemplate<String, Message> kafkaTemplate;

    public KafkaService(KafkaTemplate<String, Message> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    public void send(MessageRequestDto messageRequestDto) {
        log.error("send message : {}", messageRequestDto.getMessage());
        kafkaTemplate.send("topic_chat", new Message(messageRequestDto));
    }

//    public void publish(String boardId, MessageRequestDto messageRequestDto) {
////        chatKafkaProducer.createTopic(boardId);
//        chatKafkaProducer.publish(boardId, messageRequestDto);
//    }

    @KafkaListener(topics = "topic_chat")
    public void consume(String str) {
        log.error("Kafka Consume String : {}", str);
        sendingOperations.convertAndSend("/topic/aaa", str);
    }
}
