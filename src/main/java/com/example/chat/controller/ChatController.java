package com.example.chat.controller;

import com.example.chat.domain.dto.MessageRequestDto;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/chat")
public class ChatController {

    @MessageMapping("/chat-room/{roomId}")
//    @SendTo("/topic/not-yet")
    public void chat(@PathVariable String roomId, MessageRequestDto message) {
        // 메세지 서비스 로직
    }
}
