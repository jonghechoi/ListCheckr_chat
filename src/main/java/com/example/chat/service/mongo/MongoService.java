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
    Map<String, List<MessageContent>> boardIdMap = new HashMap<>();
    private final MongoRepository mongoRepository;

    // 새로운 사용자 입장시 기존 데이터 전송
    public MessageDocument getChatListByBoardId(String boardId) {
        return mongoRepository.getMessageDocument(boardId);
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

        // 커넥션 0이 되었을때 DB에 저장되도록 수정 필요
//        if(StompHandler.StompConnectionNumByBoardId.get(boardId) == 0) {
//            System.out.println("여기 드루옴 boardId : " + boardId);
//            saveChatListByBoardIdToMongo(messageRequestDto, boardIdMap.get(boardId));
//        }
    }

    public boolean checkDocumentByBoardId(String boardId) {
        if(boardId == null) {
            return false;
        }

        return !mongoRepository.isBoardIdExist(boardId);
    }

    public void createDocumentByBoardId(String boardId, String sender, String createTime) {
        MessageDocument messageDocument = new MessageDocument(boardId, sender, createTime);
        mongoRepository.createDocument(messageDocument);
    }

    public void storeChat(String boardId) {
        saveChatListByBoardIdToMongo(boardId, boardIdMap.get(boardId));
    }

    public void saveChatListByBoardIdToMongo(String boardId, List<MessageContent> messageContentList) {
//        String boardId = messageRequestDto.getBoardId();

//        String existCheck = "exist";
//        if(!mongoRepository.isBoardIdExist(boardId)) {
//            existCheck = "not " + existCheck;
//
//            MessageDocument messageDocument = new MessageDocument(messageRequestDto, messageContentList);
//            mongoRepository.createDocument(messageDocument);
//        }
//        else {
            // chat history 저장
            mongoRepository.appendChatHistoryIntoDocument(boardId, messageContentList);
//        }

//        log.info("[Document] {} : {}", existCheck, boardId);
    }
}
