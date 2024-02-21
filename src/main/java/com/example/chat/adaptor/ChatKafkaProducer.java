//package com.example.chat.adaptor;
//
//import com.example.chat.config.KafkaConfig;
//import com.example.chat.domain.Message;
//import com.example.chat.domain.dto.MessageRequestDto;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import jakarta.annotation.PostConstruct;
//import jakarta.annotation.PreDestroy;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.kafka.clients.admin.Admin;
//import org.apache.kafka.clients.admin.ListTopicsResult;
//import org.apache.kafka.clients.admin.NewTopic;
//import org.apache.kafka.clients.producer.KafkaProducer;
//import org.apache.kafka.clients.producer.ProducerRecord;
//import org.apache.kafka.common.KafkaFuture;
//import org.springframework.stereotype.Service;
//
//import java.util.Collections;
//import java.util.Set;
//
//@Slf4j
//@Service
//public class ChatKafkaProducer {
//    private static final String TOPIC_CHAT = "topic_chat";
//    private final KafkaConfig kafkaConfig;
////    private final Admin admin;
//    private KafkaProducer<String, String> producer;
//    private final ObjectMapper objectMapper = new ObjectMapper();
//
//    ChatKafkaProducer(KafkaConfig kafkaConfig) {
//        this.kafkaConfig = kafkaConfig;
//    }
//
//    @PostConstruct
//    public void initialize() {
//        log.info("Kafka producer initializing...");
//        this.producer = new KafkaProducer<>(this.kafkaConfig.getProducerProps());
//        Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));
//        log.info("Kafka producer initialized...");
//    }
//
//    // 토픽 생성
////    public void createTopic(String boardId) {
////        try {
////            ListTopicsResult topicRes = admin.listTopics();
////            KafkaFuture<Set<String>> future = topicRes.names();
////
////            if(future.get().contains(boardId))
////                return;
////        } catch(Exception e) {
////            throw new RuntimeException();
////        }
////
////        admin.createTopics(Collections.singleton(new NewTopic(TOPIC_CHAT + boardId, 1, (short) 1)));
////    }
//
//    // 메시지를 카프카 -> 토픽 -> 파티션으로 전달
//    public void publish(String boardId, MessageRequestDto messageRequestDto) {
//        try {
//            String message = objectMapper.writeValueAsString(new Message(messageRequestDto));
//            // producer.send(new ProducerRecord<>(TOPIC_CHAT + boardId, "partition_chat", message));
//            producer.send(new ProducerRecord<>(TOPIC_CHAT, message));
//        } catch(JsonProcessingException e) {
//            log.error("String 매퍼 에러 발생");
//        }
//    }
//
//    @PreDestroy
//    public void shutdown() {
//        log.info("Shutdown kafka producer");
//        producer.close();
//    }
//}
