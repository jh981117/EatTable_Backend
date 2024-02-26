
package com.lec.spring.config.WebSocket;

import com.lec.spring.domain.Partner;
import com.lec.spring.service.WaitingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;


import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class MyHandler extends TextWebSocketHandler {

    private final CopyOnWriteArrayList<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
    private final WaitingService waitingService;
    private final SimpMessagingTemplate messagingTemplate;

    private final Long partnerId = new Partner().getId();

    @Autowired
    public MyHandler(WaitingService waitingService, SimpMessagingTemplate messagingTemplate) {
        this.waitingService = waitingService;
        this.messagingTemplate = messagingTemplate;
        System.out.println(partnerId+"파트너아이디");
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        sendWaitingCount(session);
        System.out.println(partnerId+"파트너아이디");
    }

    private void sendWaitingCount(WebSocketSession session) {
        int waitingCount = waitingService.countWaitingsByPartnerId(partnerId); // 대기열 수 조회
        System.out.println(partnerId+"파트너아이디");
        try {
            session.sendMessage(new TextMessage(String.valueOf(waitingCount)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 대기열 수를 주기적으로 업데이트하고 클라이언트에게 전송하는 메서드
    @Scheduled(fixedRate = 10000) // 10초마다 실행
    public void sendWaitingCountToClients() {
        System.out.println(partnerId+"파트너아이디");
        int waitingCount = waitingService.countWaitingsByPartnerId(partnerId); // 대기열 수 조회
        messagingTemplate.convertAndSend("/topic/waitingCount", waitingCount); // 모든 클라이언트에게 대기열 수 전송
    }
}