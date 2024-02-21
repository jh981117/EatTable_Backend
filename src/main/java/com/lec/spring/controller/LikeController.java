package com.lec.spring.controller;

import com.lec.spring.domain.DTO.StoreLikeDto;
import com.lec.spring.domain.PartnerLikeId;
import com.lec.spring.service.PartnerLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@CrossOrigin
public class LikeController {


    private final PartnerLikeService partnerLikeService;

    @PostMapping("/storeLike")
    public ResponseEntity<?> storeLike(@RequestBody StoreLikeDto storeLikeDto) {
        try {
            boolean isLiked = partnerLikeService.toggleStoreLike(storeLikeDto.getUserId(), storeLikeDto.getPartnerId());
            return ResponseEntity.ok().body(Map.of("isLiked", isLiked));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "즐겨찾기 등록/해제 실패"));
        }
    }



    @PostMapping("/storeLike/state")
    public ResponseEntity<?> state(@RequestBody PartnerLikeId partnerLikeId) {
        boolean isFavorited = partnerLikeService.checkStoreLikeState(partnerLikeId.getUserId(), partnerLikeId.getPartnerId());
        return ResponseEntity.ok().body(Map.of("favorited", isFavorited));
    }



    @GetMapping("/storeLike/Length/{partnerId}")
    public ResponseEntity<?> likeLength(@PathVariable Long partnerId){

        return partnerLikeService.findLength(partnerId);
    }
}
