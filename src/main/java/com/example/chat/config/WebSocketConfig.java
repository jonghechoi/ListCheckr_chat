package com.example.chat.config;

import com.example.chat.handler.StompHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker // enables WebSocket message handling, backed by a message broker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    private final StompHandler stompHandler;

    // WebSocket 클라이언트가 WebSocket handshake를 위해 연결하는 endpoint URL 설정
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/chat/pub").setAllowedOriginPatterns("*").withSockJS();
    }

    // destination header가 "/app"으로 시작하는 STOMP 메시지를 @MessageMapping 메소드로 라우팅
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/chat/pub");
    }

    // ApplicationContext event는 STOMP 커넥션 라이프사이클에 대해 알림을 제공하지만 모든 client 메시지는 아니다.
    // Applications는 ChannelInterceptor를 등록하여 모든 메시지나 chain의 모든 부분을 인터셉트 할 수 있다.
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(stompHandler);
    }
}