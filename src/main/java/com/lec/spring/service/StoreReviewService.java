package com.lec.spring.service;


import com.lec.spring.domain.DTO.StoreReviewDto;
import com.lec.spring.domain.Partner;
import com.lec.spring.domain.PartnerReviewAttachment;
import com.lec.spring.domain.StoreReview;
import com.lec.spring.domain.User;
import com.lec.spring.repository.PartnerRepository;
import com.lec.spring.repository.PartnerReviewAttachmentRepository;
import com.lec.spring.repository.StoreReviewRepository;
import com.lec.spring.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



import java.util.Comparator;
import java.util.List;


@RequiredArgsConstructor
@Service
public class StoreReviewService {

    private final StoreReviewRepository storeReviewRepository;
    private final PartnerRepository partnerRepository;
    private final UserRepository userRepository;
    private final PartnerReviewAttachmentRepository partnerReviewAttachmentRepository;


    //매장리뷰작성
    @Transactional
    public StoreReview write(StoreReviewDto storeReviewDto) {
        Partner partner = partnerRepository.findById(storeReviewDto.getPartnerId()).orElse(null);

        User user = userRepository.findById(storeReviewDto.getUserId()).orElse(null);

        StoreReview storeReview =  StoreReview.builder()
                .partner(partner)
                .user(user)
                .content(storeReviewDto.getContent())
                .avg(storeReviewDto.getAvg())
                .build();

        return storeReviewRepository.save(storeReview);
    }


    public List<StoreReview> findReviewsByPartnerId(Long partnerId) {
        return storeReviewRepository.findByPartnerId(partnerId);
    }


    public ResponseEntity<?> findByReviewLength(Long partnerId) {
        long count = storeReviewRepository.countByPartnerId(partnerId);
        return ResponseEntity.ok(count);
    }

    public List<StoreReview> findReviewsByUserId(Long userId) {
        List<StoreReview> reviews = storeReviewRepository.findByUserId(userId);
        // createdAt 기준으로 내림차순으로 정렬
        System.out.println(reviews);
        reviews.sort(Comparator.comparing(StoreReview::getId).reversed());
        return reviews;
    }

    public ResponseEntity<?> findByReviewImgLength(Long partnerId) {
        long count = partnerReviewAttachmentRepository.countByPartnerId(partnerId);
        return ResponseEntity.ok(count);
    }
}
