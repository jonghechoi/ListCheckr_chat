package com.example.chat.service.mongo;

import com.example.chat.domain.MessageContent;
import com.example.chat.domain.MessageDocument;
import com.example.chat.domain.dto.MessageRequestDto;
import com.example.chat.repository.MongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MongoService {
    Map<String, List<MessageRequestDto>> boardIdMap = new HashMap<>();
    private final MongoRepository mongoRepository;

    public int checkConnectionNumByBoardId(String boardId) {
        return 0;
    }

    public void createAndSaveChatListByBoardId(MessageRequestDto messageRequestDto) {
        String boardId = messageRequestDto.getBoardId();

        if(!boardIdMap.containsKey(boardId)) {
            List<MessageRequestDto> newChatList = new ArrayList<>();
            newChatList.add(messageRequestDto);
            boardIdMap.put(boardId, newChatList);
        } else {
            List<MessageRequestDto> chatList = boardIdMap.get(boardId);
            chatList.add(messageRequestDto);
        }

        if(checkConnectionNumByBoardId(boardId) == 0) {
            saveChatListByBoardIdToMongo(messageRequestDto);
        }
    }

    public void saveChatListByBoardIdToMongo(MessageRequestDto messageRequestDto) {
        // checkConnectionNumByBoardId() 메소드를 트리거로 connection이 0이 되면 저장하도록 수정 필요
        // document 존재 여부 체크
        String boardId = messageRequestDto.getBoardId();
        if(!mongoRepository.isBoardIdExist(boardId)) {
            // MessageRequestDto -> MessageDocument
            MessageDocument messageDocument = new MessageDocument();

            mongoRepository.createDocument(messageDocument.toEntity(messageRequestDto));
        }

        // chat history 저장
        mongoRepository.appendChatHistoryIntoDocument(boardId, boardIdMap.get(boardId));
    }
}
