package com.lec.spring.controller;

import com.lec.spring.domain.Reservation;
import com.lec.spring.domain.Waiting;
import com.lec.spring.service.ReservationService;
import com.lec.spring.service.WaitingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class WebSocketController {

    private final WaitingService waitingService;
    private final ReservationService reservationService;
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/updateWaitingState")
    public void updateWaitingState(Long waitingId, Long partnerId, String newWaitingState) {
        // 대기열 상태 업데이트 로직 수행
        waitingService.updateWaitingState(waitingId, partnerId, newWaitingState);
        // 업데이트된 대기열 정보 반환
        List<Waiting> updatedWaitings = waitingService.getWaitingsByPartnerId(partnerId);

        // 메시지에 waitingId와 업데이트된 대기열 정보를 포함하여 전송
        Map<String, Object> message = new HashMap<>();
        message.put("waitingId", waitingId);
        message.put("waitings", updatedWaitings);

        messagingTemplate.convertAndSend("/topic/waitingConfirmation", message);
    }


    @MessageMapping("/updateReservationList")
    @SendTo("/topic/reservationList")
    public List<Reservation> updateReservationList(String message, Long partnerId) {
        // 예약 확정 이벤트를 받으면 대기열 정보를 업데이트하고 모든 클라이언트에게 새로운 대기열 정보를 전송
        log.info("Received reservation confirmed event: {}", message);
        List<Reservation> reservationList = reservationService.getReservationsByPartnerId(partnerId); // 대기열 정보 조회
        log.info("Sending updated waiting list to all clients: {}", reservationList);
        return reservationList;
    }
}