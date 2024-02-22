package com.example.chat.repository;

import com.example.chat.domain.dto.MessageRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MongoRepositoryTest {

    @Test
    public void createAndSaveChatListByBoardId() {
        // given
        Map<String, List<MessageRequestDto>> map = new HashMap<>();
        for(int i=0; i<10; i++) {
            List<MessageRequestDto> list = new ArrayList<>();
            MessageRequestDto messageRequestDto = new MessageRequestDto();

            messageRequestDto.setBoardId("BoardId" + i);
            messageRequestDto.setSender("Sender" + i);
            messageRequestDto.setMessage("message"+ (i*5));
            Date date = new Date();
            messageRequestDto.setTime(String.valueOf(date.getTime()));

            list.add(messageRequestDto);
            map.put("BoardId" + i, list);
        }

        // when
        // 새로운 메시지 데이터 pub
        MessageRequestDto newMessageRequestDto = new MessageRequestDto();
        newMessageRequestDto.setBoardId("newBoardId");
        newMessageRequestDto.setSender("newSender");
        newMessageRequestDto.setMessage("newmessage");
        Date date = new Date();
        newMessageRequestDto.setTime("new" + date.getTime());

        // 새로운 메시지 데이터 List add
        if(map.containsKey("BoardId0")) {
            List<MessageRequestDto> targetList = map.get("BoardId0");
            targetList.add(newMessageRequestDto);
        }

        for(Map.Entry<String, List<MessageRequestDto>> list : map.entrySet()) {
            System.out.println("리스트 값 출력 : " + list.getValue());
        }

        // then
        assertEquals(map.get("BoardId0").size(), 2);
    }

}