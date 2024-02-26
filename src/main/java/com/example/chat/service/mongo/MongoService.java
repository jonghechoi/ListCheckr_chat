package com.example.chat.service.mongo;

import com.example.chat.domain.MessageContent;
import com.example.chat.domain.MessageDocument;
import com.example.chat.domain.dto.MessageRequestDto;
import com.example.chat.handler.StompHandler;
import com.example.chat.repository.MongoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class MongoService {
    private Map<String, List<MessageContent>> boardIdMap = new HashMap<>();
    private final MongoRepository mongoRepository;

    public MessageDocument fetchChatListByBoardId(String boardId) {
        MessageDocument existMessageDocument = mongoRepository.getMessageDocument(boardId);
        existMessageDocument.setMessageContentList(boardIdMap.get(boardId));

        return existMessageDocument;
    }

    public void appendChatListMapByBoardId(MessageRequestDto messageRequestDto) {
        String boardId = messageRequestDto.getBoardId();
        MessageContent messageContent = MessageContent.toMessageContent(messageRequestDto);

        if(!boardIdMap.containsKey(boardId)) {
            List<MessageContent> newChatList = new ArrayList<>();
            newChatList.add(messageContent);
            boardIdMap.put(boardId, newChatList);
        } else {
            List<MessageContent> originalChatList = boardIdMap.get(boardId);
            originalChatList.add(messageContent);
        }
    }

    public boolean checkDocumentByBoardId(String boardId) {
        if(boardId == null) {
            return false;
        }

        return mongoRepository.isBoardIdExist(boardId);
    }

    public void createDocumentByBoardId(String boardId, String sender, String createTime) {
        MessageDocument messageDocument = new MessageDocument(boardId, sender, createTime);
        mongoRepository.createDocument(messageDocument);
    }

    public void storeChat(String boardId) {
        saveChatListByBoardIdToMongo(boardId, boardIdMap.get(boardId));
    }

    public void saveChatListByBoardIdToMongo(String boardId, List<MessageContent> messageContentList) {
        mongoRepository.appendChatHistoryIntoDocument(boardId, messageContentList);
        boardIdMap.remove(boardId);
    }
}
