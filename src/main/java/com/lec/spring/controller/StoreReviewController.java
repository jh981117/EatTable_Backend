package com.lec.spring.controller;


import com.lec.spring.domain.DTO.StoreReviewAttchmentDto;
import com.lec.spring.domain.DTO.StoreReviewDto;
import com.lec.spring.domain.PartnerAttachment;
import com.lec.spring.domain.PartnerReviewAttachment;
import com.lec.spring.domain.StoreReview;
import com.lec.spring.service.AttachmentService;
import com.lec.spring.service.StoreReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
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
    public ResponseEntity<List<?>> getAllReviews(@PathVariable Long partnerId) {
        System.out.println(partnerId);
        List<StoreReview> reviews = storeReviewService.findReviewsByPartnerId(partnerId);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/partner/img/length/{partnerId}")
    public ResponseEntity<?> reviewImgLength(@PathVariable Long partnerId){
        return storeReviewService.findByReviewImgLength(partnerId);
    }

    @GetMapping("/Length/{partnerId}")
    public ResponseEntity<?> reviewLikeLength(@PathVariable Long partnerId){
        return storeReviewService.findByReviewLength(partnerId);
    }

    // 사용자 리뷰
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<?>> getAllUserReviews(@PathVariable Long userId) {
        System.out.println("사용자ID : " + userId);
        List<StoreReview> reviews = storeReviewService.findReviewsByUserId(userId);
        return ResponseEntity.ok(reviews);
    }


    // 리뷰 수정
    @PutMapping("/reviews/update")
    public ResponseEntity<?> updateReview(@RequestBody StoreReviewDto storeReviewDto) { // storeReviewDto : 클라이언트단에서 body에 담아서 서버로 보내주는 필드들
        StoreReview review =  storeReviewService.findByReviewId(storeReviewDto);    // 데이터베이스에 저장되어있는 리뷰아이디의 해당 글
        return ResponseEntity.ok(review);
    }


    // 리뷰 이미지 수정
    @PutMapping("/reviews/update/attachments")
    public ResponseEntity<?> updateReviewAttachments(@RequestBody StoreReviewAttchmentDto storeReviewAttchmentDto, @RequestParam("files") List<MultipartFile> files) {
        try {
            attachmentService.updateAttachments(storeReviewAttchmentDto.getStoreId(), files); // 이미지 업데이트 서비스 호출
            // 파일 업로드 성공 응답
            return ResponseEntity.ok().body("이미지 수정 성공");
        } catch (Exception e) {
            e.printStackTrace();
            // 파일 업로드 실패 응답
            return ResponseEntity.badRequest().body("이미지 수정 실패");
        }
    }

    // 리뷰 삭제
    @DeleteMapping("/reviews/delete/{reviewId}")
    public ResponseEntity<?> deleteReview(@PathVariable Long reviewId) {
        return ResponseEntity.ok(storeReviewService.findByReviewId(reviewId));
    }
}
