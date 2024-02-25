package com.lec.spring.controller;

import com.lec.spring.domain.DTO.ReservationDto;
import com.lec.spring.domain.DTO.WaitingDto;
import com.lec.spring.domain.Reservation;
import com.lec.spring.domain.TrueFalse;
import com.lec.spring.domain.Waiting;
import com.lec.spring.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/reservation")
@CrossOrigin
public class ReservationController {

    private final ReservationService reservationService;

    // 매장 대기열 리스트 (파트너 계정이 관리)
    @GetMapping("/reservationList/{partnerId}")
    public ResponseEntity<List<Reservation>> listWaitings(@PathVariable Long partnerId) {
        List<Reservation> reservations = reservationService.findReservationsByPartnerId(partnerId); // 해당 파트너의 대기열 목록을 조회하는 메서드를 호출합니다.
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @GetMapping("/reservationCount/{id}")
    public ResponseEntity<Integer> getReservationCount(@PathVariable Long id) {
        // id를 이용하여 대기열 수를 조회하고 반환
        int reservationCount = reservationService.countReservationsByPartnerId(id);
        return ResponseEntity.ok(reservationCount);
    }

    // 매장 대기열 추가 (파트너 계정이 관리)
    @PostMapping("/addReservation/{partnerId}")
    public ResponseEntity<?> addReservation(@PathVariable Long partnerId, @RequestBody ReservationDto reservation) {
        System.out.println(partnerId);
        System.out.println(reservation);
        Reservation saveReservation = reservationService.saveReservation(reservation, partnerId);
        return new ResponseEntity<>(saveReservation, HttpStatus.CREATED);
    }


    // 매장 대기열 상태 업데이트 waitingState TRUE, FALSE 관리 (파트너 계정이 관리) (이거 사용함 위에꺼 사용 x)
    @PutMapping("/updateReservationState/{partnerId}/{reservationId}")
    public ResponseEntity<?> updateReservation(@PathVariable Long reservationId, @PathVariable Long partnerId, @RequestBody ReservationDto reservationDto){
        // ReservationDto에서 reservationState를 가져와서 사용
        String newReservationState = reservationDto.getReservationState();
        Reservation reservation = reservationService.updateReservationState(reservationId, partnerId, newReservationState);
        return new ResponseEntity<>(reservation, HttpStatus.OK);
    }

    // 유저 개인의 예약리스트 (유저 마이페이지에서 보여줄 것)
    @GetMapping("/userReservation/{userId}")
    public ResponseEntity<List<Reservation>> findUserReservation(@PathVariable Long userId) {
        List<Reservation> userReservations = reservationService.findUserReservation(userId);
        return new ResponseEntity<>(userReservations, HttpStatus.OK);
    }

    // 유저 대기열 삭제 (유저 마이페이지에서 개인이 삭제)
    @DeleteMapping("/reservationDelete/{partnerId}/{reservationId}")
    public ResponseEntity<?> deleteReservation(@PathVariable Long partnerId, @PathVariable Long reservationId) {
        reservationService.deleteByReservationIdAndPartnerId(reservationId, partnerId);
        return ResponseEntity.noContent().build();
    }

}
