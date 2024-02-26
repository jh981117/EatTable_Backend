package com.lec.spring.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lec.spring.domain.Reservation;
import com.lec.spring.domain.Waiting;
import com.lec.spring.service.ReservationService;
import com.lec.spring.service.WaitingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class WebSocketController {

    private final WaitingService waitingService;
    private final ReservationService reservationService;
    private final ObjectMapper objectMapper;

    @MessageMapping("/updateWaitingCount")
    @SendTo("/topic/waitingList")
    public List<Waiting> updateWaitingList(String message, Long partnerId) {
        // 예약 확정 이벤트를 받으면 대기열 정보를 업데이트하고 모든 클라이언트에게 새로운 대기열 정보를 전송
        log.info("Received reservation confirmed event: {}", message);
        List<Waiting> waitingList = waitingService.getWaitingsByPartnerId(partnerId); // 대기열 정보 조회
        log.info("Sending updated waiting list to all clients: {}", waitingList);
        return waitingList;
    }


    @SendTo("/topic/reservationList")
    public List<Reservation> updateReservationList(String message, Long partnerId) {
        // 예약 확정 이벤트를 받으면 대기열 정보를 업데이트하고 모든 클라이언트에게 새로운 대기열 정보를 전송
        log.info("Received reservation confirmed event: {}", message);
        List<Reservation> reservationList = reservationService.getReservationsByPartnerId(partnerId); // 대기열 정보 조회
        log.info("Sending updated waiting list to all clients: {}", reservationList);
        return reservationList;
    }
}