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
    public void updateReservationState(Long reservationId, Long partnerId, String newReservationState) {
        // 대기열 상태 업데이트 로직 수행
        reservationService.updateReservationState(reservationId, partnerId, newReservationState);
        // 업데이트된 대기열 정보 반환
        List<Reservation> updatedReservations = reservationService.getReservationsByPartnerId(partnerId);

        // 메시지에 waitingId와 업데이트된 대기열 정보를 포함하여 전송
        Map<String, Object> message = new HashMap<>();
        message.put("reservationId", reservationId);
        message.put("Reservations", updatedReservations);

        messagingTemplate.convertAndSend("/topic/reservationConfirmation", message);

    }
}