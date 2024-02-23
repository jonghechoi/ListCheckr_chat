package com.example.chat.repository;

import com.example.chat.domain.MessageDocument;
import com.example.chat.domain.dto.MessageRequestDto;
import com.example.chat.domain.dto.MessageResponseDto;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MongoRepository {
    @Value("${spring.data.mongodb.collection}")
    private String COLLECTION_NAME;

    private final MongoTemplate mongoTemplate;

    @PostConstruct
    public void createCollection() {
        if(!mongoTemplate.collectionExists(COLLECTION_NAME)) {
            mongoTemplate.createCollection(COLLECTION_NAME);
        }
    }

    public boolean isBoardIdExist(String boardId) {
        Query query = new Query(Criteria.where("boardId").is(boardId));
        return Optional.ofNullable(mongoTemplate.find(query, MessageDocument.class))
                .isPresent();
    }

    public void createDocument(MessageDocument messageDocument) {
        mongoTemplate.save(messageDocument, COLLECTION_NAME);
    }

    public void appendChatHistoryIntoDocument(String boardId, List<MessageRequestDto> messageList) {

        Query query = new Query(Criteria.where("boardId").is(boardId));

        Update update = new Update();
        update.addToSet("messageList", messageList);

        mongoTemplate.updateFirst(query, update, MessageDocument.class);
    }
}
