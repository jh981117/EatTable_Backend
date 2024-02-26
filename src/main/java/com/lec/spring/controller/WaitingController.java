package com.lec.spring.controller;

import com.lec.spring.domain.DTO.WaitingDto;
import com.lec.spring.domain.TrueFalse;
import com.lec.spring.domain.Waiting;
import com.lec.spring.service.WaitingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/waiting")
@CrossOrigin(origins = "http://localhost:3000") // cross-origin 요청 허용
public class WaitingController {


    private final WaitingService waitingService;

    // 매장 대기열 리스트 (파트너 계정이 관리)
    @GetMapping("/waitingList/{partnerId}")
    public ResponseEntity<List<Waiting>> listWaitings(@PathVariable Long partnerId) {
        List<Waiting> waitings = waitingService.findWaitingsByPartnerId(partnerId); // 해당 파트너의 대기열 목록을 조회하는 메서드를 호출합니다.
        return new ResponseEntity<>(waitings, HttpStatus.OK);
    }

    @GetMapping("/waitingCount/{id}")
    public ResponseEntity<Integer> getWaitingCount(@PathVariable Long id) {
        // id를 이용하여 대기열 수를 조회하고 반환
        int waitingCount = waitingService.countWaitingsByPartnerId(id);
        return ResponseEntity.ok(waitingCount);
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



    // 매장 대기열 상태 업데이트 waitingState TRUE, FALSE 관리 (파트너 계정이 관리) (이거 사용함 위에꺼 사용 x)
    @PutMapping("/updateWaitingState/{partnerId}/{waitingId}")
    public ResponseEntity<?> updateWaiting(@PathVariable Long waitingId, @PathVariable Long partnerId, @RequestBody WaitingDto waitingDto){
        // WaitingDto에서 waitingState를 가져와서 사용
        String newWaitingState = waitingDto.getWaitingState();
        Waiting waiting = waitingService.updateWaitingState(waitingId, partnerId, newWaitingState);
        return new ResponseEntity<>(waiting, HttpStatus.OK);
    }

    // 유저 개인의 예약리스트 (유저 마이페이지에서 보여줄 것)
    @GetMapping("/userWaiting/{userId}")
    public ResponseEntity<List<Waiting>> findUserWaiting(@PathVariable Long userId) {
        List<Waiting> userWaitings = waitingService.findUserWaiting(userId);
        return new ResponseEntity<>(userWaitings, HttpStatus.OK);
    }

    // 유저 대기열 삭제 (유저 마이페이지에서 개인이 삭제)
    @DeleteMapping("/waitingDelete/{partnerId}/{waitingId}")
    public ResponseEntity<?> deleteWaiting(@PathVariable Long partnerId, @PathVariable Long waitingId) {
        waitingService.deleteByWaitingIdAndPartnerId(waitingId, partnerId);
        return ResponseEntity.noContent().build();
    }

}
