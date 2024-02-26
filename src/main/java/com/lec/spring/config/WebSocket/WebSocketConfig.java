package com.lec.spring.config.WebSocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 클라이언트에서 WebSocket 연결을 수락하는 데 사용할 엔드포인트 등록
        registry.addEndpoint("/ws").setAllowedOrigins("http://localhost:3000");
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 클라이언트로부터 메시지를 수신하고 처리하는 데 사용할 메시지 브로커를 설정
        registry.enableSimpleBroker("/topic"); // "/topic"으로 시작하는 주제에 대한 메시지 전송
        registry.setApplicationDestinationPrefixes("/app"); // "/app"으로 시작하는 주소로 들어오는 메시지를 처리
    }
}
