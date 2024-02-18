package com.lec.spring.service;


import com.lec.spring.domain.DTO.StoreReviewDto;
import com.lec.spring.domain.Partner;
import com.lec.spring.domain.PartnerReviewAttachment;
import com.lec.spring.domain.StoreReview;
import com.lec.spring.domain.User;
import com.lec.spring.repository.PartnerRepository;
import com.lec.spring.repository.StoreReviewRepository;
import com.lec.spring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class StoreReviewService {

    private final StoreReviewRepository storeReviewRepository;
    private final PartnerRepository partnerRepository;
    private final UserRepository userRepository;
    private final S3Service s3Service;


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
        // partnerId에 해당하는 리뷰 목록을 조회하는 코드

        // 예시로 각 리뷰의 User 및 Partner 정보를 함께 조회하는 코드
        List<StoreReview> reviews = storeReviewRepository.findByPartnerId(partnerId);
        for (StoreReview review : reviews) {
            User user = review.getUser();
            Partner partner = review.getPartner();
            // User 및 Partner 정보 활용
        }

        return reviews;
    }
}
