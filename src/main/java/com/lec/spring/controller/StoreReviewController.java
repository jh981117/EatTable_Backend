package com.lec.spring.controller;


import com.lec.spring.domain.DTO.StoreReviewAttchmentDto;
import com.lec.spring.domain.DTO.StoreReviewDto;
import com.lec.spring.domain.StoreReview;
import com.lec.spring.service.AttachmentService;
import com.lec.spring.service.StoreReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/store")
@CrossOrigin
public class StoreReviewController {

    private final StoreReviewService storeReviewService;
    private final AttachmentService attachmentService;



    //리뷰저장
    @PostMapping("/reviews")
    public ResponseEntity<?> createReview(@RequestBody StoreReviewDto storeReviewDto) {
        try {
            // DTO를 서비스 메소드에 전달
            StoreReview createdReview = storeReviewService.write(storeReviewDto);
            // 리뷰 생성 성공 응답
            return ResponseEntity.ok().body(Map.of("message", "리뷰가 성공적으로 등록되었습니다.", "reviewId", createdReview.getId()));
        } catch (Exception e) {
            e.printStackTrace();
            // 리뷰 생성 실패 응답
            return ResponseEntity.badRequest().body("리뷰 등록에 실패하였습니다.");
        }
    }



    //이미지저장
    @PostMapping("/reviews/{reviewId}/attachments")
    public ResponseEntity<?> uploadReviewAttachments(@PathVariable Long reviewId, @RequestParam("files") List<MultipartFile> files) {
        try {
            attachmentService.saveAttachments(reviewId, files);
            // 파일 업로드 성공 응답
            return ResponseEntity.ok().body("파일이 성공적으로 업로드되었습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            // 파일 업로드 실패 응답
            return ResponseEntity.badRequest().body("파일 업로드에 실패하였습니다.");
        }
    }


//    해당 매장 리뷰 리스트
    @GetMapping("/partner/{partnerId}")
    public ResponseEntity<List<StoreReviewDto>> getAllReviews(@PathVariable Long partnerId) {
        System.out.println(partnerId);
        List<StoreReviewDto> reviews = storeReviewService.findReviewsByPartnerId(partnerId);
        return ResponseEntity.ok(reviews);
    }


    @GetMapping("{storeId}/review/images")
    public ResponseEntity<List<StoreReviewAttchmentDto>> getImagesByStoreId(@PathVariable Long storeId) {
        System.out.println(storeId);
        List<StoreReviewAttchmentDto> attachmentDtos = attachmentService.findByStoreImg(storeId);
        return ResponseEntity.ok(attachmentDtos);
    }

    // 사용자 리뷰
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<?>> getAllUserReviews(@PathVariable Long userId) {
        System.out.println(userId + "1312321313");
        List<StoreReview> reviews = storeReviewService.findReviewsByUserId(userId);
        return ResponseEntity.ok(reviews);
    }


}
