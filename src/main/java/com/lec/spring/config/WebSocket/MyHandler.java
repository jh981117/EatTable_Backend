
package com.lec.spring.config.WebSocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lec.spring.domain.Partner;
import com.lec.spring.domain.Reservation;
import com.lec.spring.domain.Waiting;
import com.lec.spring.service.ReservationService;
import com.lec.spring.service.WaitingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;


import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
@Component
public class MyHandler extends TextWebSocketHandler {

    private final CopyOnWriteArrayList<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
    private final WaitingService waitingService;
    private final ReservationService reservationService;
    private final SimpMessagingTemplate messagingTemplate;

    private final ObjectMapper objectMapper;

    private Long partnerId;


    @Autowired
    public MyHandler(WaitingService waitingService,ReservationService reservationService, SimpMessagingTemplate messagingTemplate, ObjectMapper objectMapper) {
        this.waitingService = waitingService;
        this.messagingTemplate = messagingTemplate;
        this.objectMapper = objectMapper;
        this.reservationService = reservationService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        log.info("{} 연결됨", session.getId());

        // 세션에서 파트너 아이디 추출
        String partnerIdString = session.getHandshakeHeaders().getFirst("partnerId");
        if (partnerIdString != null) {
            partnerId = Long.valueOf(partnerIdString);
            log.info("파트너 아이디: {}", partnerId);
        } else {
            log.error("파트너 아이디가 전달되지 않았습니다.");
            // 파트너 아이디가 전달되지 않은 경우 처리 로직 추가
        }

        sendWaitingCount(session);
        sendWaitingList(session); // 대기열 리스트도 전송
        sendReservationList(session);
    }

    private void sendWaitingCount(WebSocketSession session) {
        if (partnerId != null) {
            int waitingCount = waitingService.countWaitingsByPartnerId(partnerId); // 대기열 수 조회
            try {
                session.sendMessage(new TextMessage(String.valueOf(waitingCount)));
            } catch (IOException e) {
                log.error("대기열 수 전송 중 오류 발생: {}", e.getMessage());
            }
        } else {
            log.error("파트너 아이디가 설정되지 않았습니다.");
            // 파트너 아이디가 설정되지 않은 경우 처리 로직 추가
        }
    }

    // 대기열 수를 주기적으로 업데이트하고 클라이언트에게 전송하는 메서드
    @Scheduled(fixedRate = 10000) // 10초마다 실행
    public void sendWaitingCountToClients() {
        if (partnerId != null) {
            int waitingCount = waitingService.countWaitingsByPartnerId(partnerId); // 대기열 수 조회
            messagingTemplate.convertAndSend("/topic/waitingCount", waitingCount); // 모든 클라이언트에게 대기열 수 전송
        } else {
            log.error("파트너 아이디가 설정되지 않았습니다.");
            // 파트너 아이디가 설정되지 않은 경우 처리 로직 추가
        }
    }

    @Scheduled(fixedRate = 1000) // 10초마다 실행
    public void sendWaitingListToClients() {
        // 파트너 아이디를 이용하여 해당 파트너의 대기열 리스트를 조회
        List<Waiting> waitingList = waitingService.getWaitingsByPartnerId(partnerId);
        // 모든 클라이언트에게 대기열 리스트를 전송
        messagingTemplate.convertAndSend("/topic/updateWaitingList", waitingList);
    }



    private void sendWaitingList(WebSocketSession session) {
        if (partnerId != null) {
            List<Waiting> waitingList = waitingService.getWaitingsByPartnerId(partnerId); // 대기열 리스트 조회
            try {
                String jsonWaitings = objectMapper.writeValueAsString(waitingList);
                session.sendMessage(new TextMessage(jsonWaitings));
            } catch (IOException e) {
                log.error("대기열 리스트 전송 중 오류 발생: {}", e.getMessage());
            }
        } else {
            log.error("파트너 아이디가 설정되지 않았습니다.");
            // 파트너 아이디가 설정되지 않은 경우 처리 로직 추가
        }
    }

    private void sendReservationList(WebSocketSession session) {
        if (partnerId != null) {
            List<Reservation> reservationList = reservationService.getReservationsByPartnerId(partnerId); // 대기열 리스트 조회
            try {
                String jsonReservations = objectMapper.writeValueAsString(reservationList);
                session.sendMessage(new TextMessage(jsonReservations));
            } catch (IOException e) {
                log.error("대기열 리스트 전송 중 오류 발생: {}", e.getMessage());
            }
        } else {
            log.error("파트너 아이디가 설정되지 않았습니다.");
            // 파트너 아이디가 설정되지 않은 경우 처리 로직 추가
        }
    }
}
