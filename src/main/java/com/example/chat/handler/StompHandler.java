package com.example.chat.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Slf4j
@Component
public class StompHandler implements ChannelInterceptor {

    // Invoked before the Message is actually sent to the channel. This allows for modification of the Message if necessary
    // 메시지가 전달되기 전에 JWT 등 보안인증처리를 할 수도 있다.
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);
        log.info("StompCommand 내용 : {}", headerAccessor.getCommand());

        return message;
    }

    @EventListener
    public void handleWebSocketConnectionListener(SessionConnectedEvent event) {
        // 여기다 어떤 사용자 입장했는지 로그로 남겨보기
        // SessionConnectedEvent : A connected event represents the server response to a client's connect request. See SessionConnectEvent.
        // SessionConnectEvent : Event raised when a new WebSocket client using a Simple Messaging Protocol (e.g. STOMP) as the WebSocket sub-protocol issues a connect request.
        log.info("사용자 입장 : {}", event.getUser());
    }

    @EventListener
    public void handleWebSocketDisconnectionListener(SessionDisconnectEvent event) {
        log.info("사용자 퇴장 : {}", event.getUser());
    }
}
