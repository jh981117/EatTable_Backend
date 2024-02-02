package com.lec.spring.controller;


import com.lec.spring.domain.PartnerReq;
import com.lec.spring.service.PartnerReqService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/req")
@CrossOrigin
public class PartnerReqController {

    private final PartnerReqService partnerReqService;



    //전체리스트 ( 필요시)
    @Transactional
    @GetMapping("/totalList")
    @PreAuthorize("hasAnyRole('ADMIN')")
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
    public ResponseEntity<?> write(@RequestBody PartnerReq partnerReq){
        return new ResponseEntity<>(partnerReqService.write(partnerReq),HttpStatus.CREATED);
    }


    //작성 수정?? 상의
    @Transactional
    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody PartnerReq partnerReq){
        return new ResponseEntity<>(partnerReqService.update(partnerReq),HttpStatus.OK);
    }



    //신청 삭제 ( 마음바뀜)
    @Transactional
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        return new ResponseEntity<>(partnerReqService.delete(id),HttpStatus.OK);
    }
}
