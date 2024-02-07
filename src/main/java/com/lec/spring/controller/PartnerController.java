package com.lec.spring.controller;


import com.lec.spring.domain.DTO.PartnerWriteDto;
import com.lec.spring.domain.Partner;

import com.lec.spring.service.PartnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/partner")
@CrossOrigin // cross-origin 요청 허용
public class PartnerController {


    private final PartnerService partnerService;


//    매장리스트
    @GetMapping("/totallist")
    public ResponseEntity<?> totallist (){
        return new ResponseEntity<>(partnerService.totallist(), HttpStatus.OK);
    }




    @GetMapping("/list")
    public ResponseEntity<Page<Partner>> list(@RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "5") int size, String keyword) {
        Pageable pageable = PageRequest.of(page, size);
        return new ResponseEntity<>(partnerService.list(keyword,pageable), HttpStatus.OK);
    }



    //매장등록


    @PostMapping("/write")
    public ResponseEntity<?> write(@ModelAttribute PartnerWriteDto partnerWriteDto,
                                   @RequestPart("files") List<MultipartFile> files) {
        Partner partner = partnerWriteDto.toEntity();
        return new ResponseEntity<>(partnerService.write(partner, files), HttpStatus.CREATED);
    }


    //매장정보 디테일
    @GetMapping("/detail/{id}")
    public ResponseEntity<?> detail(@PathVariable Long id){
        return new ResponseEntity<>(partnerService.detail(id),HttpStatus.OK);
    }

    //매장수정
    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody Partner partner){
        return new ResponseEntity<>(partnerService.update(partner),HttpStatus.OK);
    }



    //매장삭제  직접 x  신청받고 삭제가능
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        return new ResponseEntity<>(partnerService.delete(id),HttpStatus.OK);
    }



}
