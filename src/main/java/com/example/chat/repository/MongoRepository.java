package com.example.chat.repository;

import com.example.chat.domain.MessageContent;
import com.example.chat.domain.MessageDocument;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Slf4j
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
        Query query = new Query(Criteria.where("_id").is(boardId));
        return mongoTemplate.exists(query, COLLECTION_NAME);
    }

    public MessageDocument getMessageDocument(String boardId) {
        return mongoTemplate.findOne(Query.query(Criteria.where("_id").is(boardId)), MessageDocument.class);
    }

    public void createDocument(MessageDocument messageDocument) {
        mongoTemplate.save(messageDocument, COLLECTION_NAME);
    }

    public void appendChatHistoryIntoDocument(String boardId, List<MessageContent> messageContentList) {
        MessageDocument messageDocument = this.getMessageDocument(boardId);
        messageDocument.setMessageContentList(messageContentList);
        mongoTemplate.save(messageDocument, COLLECTION_NAME);
    }
}
