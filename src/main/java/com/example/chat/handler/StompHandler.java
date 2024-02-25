package com.example.chat.handler;

import com.example.chat.domain.MessageDocument;
import com.example.chat.service.mongo.MongoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class StompHandler implements ChannelInterceptor {
    private final MongoService mongoService;
    public static Map<String, Integer> StompConnectionNumByBoardId = new HashMap<>();
    public String connectionStatus;


    // Invoked before the Message is actually sent to the channel. This allows for modification of the Message if necessary (메시지가 전달되기 전에 JWT 등 보안인증처리 등 가능)
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);
        String headerCommand = headerAccessor.getCommand().toString();
        System.out.println(headerAccessor);
        connectionStatus = headerCommand;
        String boardId = headerAccessor.getFirstNativeHeader("boardId");

        log.info("[Stomp] Header Command : {} and BoardId : {}", headerCommand, boardId);

        if(boardId == null) { return message; }

        if(headerCommand.equals("CONNECT") && mongoService.checkDocumentByBoardId(boardId)) {
            String sender = headerAccessor.getFirstNativeHeader("sender");
            String createTime = headerAccessor.getFirstNativeHeader("createTime");
            mongoService.createDocumentByBoardId(boardId, sender, createTime);

        }

        if(headerCommand.equals("CONNECT")) {
            if(!StompConnectionNumByBoardId.containsKey(boardId) ) {
                StompConnectionNumByBoardId.put(boardId, 1);
            }
            else {
                int connectionNum = StompConnectionNumByBoardId.get(boardId);
                StompConnectionNumByBoardId.put(boardId, ++ connectionNum);
            }

            log.info("[Stomp] Command : {}, BoardId : {}, ConnectionNum : {}", connectionStatus, boardId, StompConnectionNumByBoardId.get(boardId));
        }
        else if(headerCommand.equals("DISCONNECT")) {
            int connectionNum = StompConnectionNumByBoardId.get(boardId);
            StompConnectionNumByBoardId.put(boardId, -- connectionNum);

            log.info("[Stomp] Command : {}, BoardId : {}, ConnectionNum : {}", connectionStatus, boardId, StompConnectionNumByBoardId.get(boardId));

            if(StompHandler.StompConnectionNumByBoardId.get(boardId) == 0) {
                mongoService.storeChat(boardId);
            }
        }

        return message;
    }

    @EventListener
    public void handleWebSocketConnectionListener(SessionConnectEvent event) {
//        log.info("사용자 입장 : {}", event.getUser());
    }

    @EventListener
    public void handleWebSocketDisconnectionListener(SessionDisconnectEvent event) {
//        log.info("사용자 퇴장 : {}", event.getUser());
    }
}
