package com.example.chat.service.mongo;

import com.example.chat.domain.MessageContent;
import com.example.chat.domain.MessageDocument;
import com.example.chat.domain.dto.MessageRequestDto;
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

    private int saveTest = 0;

    public int checkConnectionNumByBoardId(String boardId) {
        return 0;
    }

    // 새로운 사용자 입장시 기존 데이터 전송
    public void getChatListByBoardId() {

    }

    public void createAndSaveChatListByBoardId(MessageRequestDto messageRequestDto) {
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

        // 커넥션 0이 되었을때 DB에 저장되는지 체크 필요
        saveTest ++;
        // if(checkConnectionNumByBoardId(boardId) == 0) {
        if((saveTest%3) == 0) {
            saveChatListByBoardIdToMongo(messageRequestDto, boardIdMap.get(boardId));
        }
    }

    public void saveChatListByBoardIdToMongo(MessageRequestDto messageRequestDto, List<MessageContent> messageContentList) {
        // checkConnectionNumByBoardId() 메소드를 트리거로 connection이 0이 되면 저장하도록 수정 필요
        // document 존재 여부 체크
        String boardId = messageRequestDto.getBoardId();


        String existCheck = "exist";
        if(!mongoRepository.isBoardIdExist(boardId)) {
            existCheck = "not " + existCheck;

            MessageDocument messageDocument = new MessageDocument(messageRequestDto, messageContentList);
            mongoRepository.createDocument(messageDocument);
        }
        else {
            // chat history 저장
            mongoRepository.appendChatHistoryIntoDocument(boardId, messageContentList);
        }

        log.info("[Document] {} : {}", existCheck, boardId);
    }
}
