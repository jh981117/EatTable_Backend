package com.lec.spring.controller;


import com.lec.spring.domain.DTO.PartnerReqDto;
import com.lec.spring.domain.PartnerReq;
import com.lec.spring.domain.PartnerReqState;
import com.lec.spring.service.PartnerReqService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/req")
@CrossOrigin
public class PartnerReqController {

    private final PartnerReqService partnerReqService;



    //전체리스트 ( 필요시)
    @Transactional
    @GetMapping("/totalList")
//    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> totalList(){
        return new ResponseEntity<>(partnerReqService.list(), HttpStatus.OK);
    }


    //특정상태 리스트
    @Transactional
    @GetMapping("/stateList/{state}")
    public ResponseEntity<?> stateList(@PathVariable String state){
        return new ResponseEntity<>(partnerReqService.listByStatus(state),HttpStatus.OK);


    }


    //신청작성
    @Transactional
    @PostMapping("/write")
    public ResponseEntity<?> write(@RequestBody PartnerReqDto partnerReqDto){

        Long userId = partnerReqDto.getUserId();
        PartnerReq partnerReq = partnerReqDto (partnerReqDto);
        // 서비스 메서드 호출
        PartnerReq savedPartnerReq = partnerReqService.write(partnerReq, userId);

        return new ResponseEntity<>(savedPartnerReq, HttpStatus.CREATED);
    }

    private PartnerReq partnerReqDto(PartnerReqDto partnerReqDto) {
        return PartnerReq.builder()
                .managerName(partnerReqDto.getManagerName())
                .storeName(partnerReqDto.getStoreName())
                .phone(partnerReqDto.getPhone())
                .memo(partnerReqDto.getMemo())
                .partnerReqState(PartnerReqState.OPEN_READY)
                // 나머지 필드 설정은 각 필드에 대한 빌더 메서드를 호출하여 추가
                .build();
    }
    //작성 수정?? 상의
    @Transactional
    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody PartnerReq partnerReq){
        return new ResponseEntity<>(partnerReqService.update(partnerReq),HttpStatus.OK);
    }

    @Transactional
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody PartnerReq partnerReq) {
        partnerReq.setId(id);
        return new ResponseEntity<>(partnerReqService.update(partnerReq), HttpStatus.OK);
    }

    //신청 삭제 ( 마음바뀜)
    @Transactional
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        return new ResponseEntity<>(partnerReqService.delete(id),HttpStatus.OK);
    }



    @PostMapping("/cancelPartner")
    public ResponseEntity<?> cancelPatner(@RequestBody Map<String, Long> requestBody){
        Long id = requestBody.get("userId");

        return new ResponseEntity<>(partnerReqService.cancelPartner(id), HttpStatus.CREATED);

    }
}
