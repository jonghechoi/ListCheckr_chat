package com.example.chat.repository;

import com.example.chat.domain.dto.MessageRequestDto;
import com.example.chat.domain.dto.MessageResponseDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MongoRepository {
    Map<String, List<MessageResponseDto>> boardIdMap = new HashMap<>();

    public void checkConnectionNumByBoardId() {

    }

    public void createAndSaveChatListByBoardId(MessageResponseDto messageResponseDto) {
        String boardId = messageResponseDto.getBoardId();

        if(!boardIdMap.containsKey(boardId)) {
            List<MessageResponseDto> newChatList = new ArrayList<>();
            newChatList.add(messageResponseDto);
            boardIdMap.put(boardId, newChatList);
        } else {
            List<MessageResponseDto> chatList = boardIdMap.get(boardId);
            chatList.add(messageResponseDto);
        }
    }

    public void saveChatListByBoardIdToMongo() {

    }
}
