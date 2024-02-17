package com.lec.spring.controller;

import com.lec.spring.domain.DTO.StoreReviewDto;
import com.lec.spring.domain.Partner;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/store")
@CrossOrigin
public class StoreReviewController {



    @PostMapping("/reviews")
    public ResponseEntity<?> createReview(@RequestBody StoreReviewDto storeReviewDto) {
        // 리뷰 데이터를 데이터베이스에 저장하는 로직
        return ResponseEntity.ok().body("리뷰가 성공적으로 등록되었습니다.");
    }


    @PostMapping("/uploadReviewImage")
    public ResponseEntity<?> uploadReviewImage(
            @RequestParam("file") MultipartFile file,
            @RequestParam("userId") Long userId,
            @RequestParam("partnerId") Long partnerId) {
        // 파일을 저장하는 로직 구현
        // userId와 partnerId를 사용하여 파일이 특정 사용자와 매장 리뷰에 연결되도록 처리

        // 파일 저장 후, StoreReviewAttachment 엔티티 인스턴스 생성 및 저장 로직 구현
        // 예: 파일 메타데이터 저장, StoreReview 참조 설정 등

        return ResponseEntity.ok().body("파일 업로드 및 매장 리뷰 이미지 처리 성공");
    }

}
