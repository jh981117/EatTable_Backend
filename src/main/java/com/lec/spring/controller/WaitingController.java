package com.lec.spring.controller;

import com.lec.spring.domain.DTO.WaitingDto;
import com.lec.spring.domain.TrueFalse;
import com.lec.spring.domain.Waiting;
import com.lec.spring.service.WaitingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/waiting")
@CrossOrigin // cross-origin 요청 허용
public class WaitingController {


    private final WaitingService waitingService;

    // 매장 대기열 리스트 (파트너 계정이 관리)
    @GetMapping("/waitingList/{partnerId}")
    public ResponseEntity<List<Waiting>> listWaitings() {
        List<Waiting> waitings = waitingService.findAllWaitings();
        return new ResponseEntity<>(waitings, HttpStatus.OK);
    }

    // 매장 대기열 추가 (파트너 계정이 관리)
    @PostMapping("/addWaiting/{partnerId}")
    public ResponseEntity<?> addReservation(@PathVariable Long partnerId, @RequestBody WaitingDto waiting) {
        System.out.println(partnerId);
        System.out.println(waiting);
        Waiting saveWating = waitingService.saveWaiting(waiting, partnerId);
        return new ResponseEntity<>(saveWating, HttpStatus.CREATED);
    }

    // 매장 대기열 상태 업데이트 waitingState TRUE, FALSE 관리 (파트너 계정이 관리)
    @PutMapping("/updateWaiting/{partnerId}/{userId}")
    public ResponseEntity<?> updateWaitingState(@PathVariable Long id, @RequestParam TrueFalse newWaitingState) {
        Waiting updatedWaiting = waitingService.updateWaiting(id, newWaitingState);
        return new ResponseEntity<>(updatedWaiting, HttpStatus.OK);
    }


    // 유저 개인의 예약리스트 (유저 마이페이지에서 보여줄 것)
    @GetMapping("/userWaiting/{userId}")
    public ResponseEntity<List<Waiting>> findUserWaiting(@PathVariable Long userId) {
        List<Waiting> userWaitings = waitingService.findUserWaiting(userId);
        return new ResponseEntity<>(userWaitings, HttpStatus.OK);
    }

    // 유저 대기열 삭제 (유저 마이페이지에서 개인이 삭제)
    @DeleteMapping("/waitingDelete/{userId}")
    public ResponseEntity<?> deleteWaiting(@PathVariable Long userId) {
        waitingService.deleteByUserId(userId);
        return ResponseEntity.noContent().build();
    }

}
