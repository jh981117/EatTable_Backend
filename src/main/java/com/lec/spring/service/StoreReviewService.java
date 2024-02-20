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
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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


    public List<StoreReviewDto> findReviewsByPartnerId(Long partnerId) {
        List<StoreReview> reviews = storeReviewRepository.findByPartnerId(partnerId);

        return reviews.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }


    @Transactional
    public StoreReviewDto toDto(StoreReview review) {
        List<String> imageUrls = review.getPartnerReviewAttachments()
                .stream()
                .map(PartnerReviewAttachment::getImageUrl)
                .collect(Collectors.toList());

        return StoreReviewDto.builder()
                .storeReviewId(review.getId())
                .content(review.getContent())
                .avg(review.getAvg())
                .userId(review.getUser().getId())
                .nickname(review.getUser().getNickName())
                .profileImageUrl(review.getUser().getProfileImageUrl())
                .temperature(review.getUser().getTemperature())
                .storeName(review.getPartner().getStoreName())
                .partnerReviewAttachments(imageUrls)
                .createdAt(review.getCreatedAt())
                .updatedAt(review.getUpdatedAt())
                .build();
    }


    public List<StoreReview> findReviewsByUserId(Long userId) {
        List<StoreReview> reviews = storeReviewRepository.findByUserId(userId);
        // createdAt 기준으로 내림차순으로 정렬
        System.out.println(reviews);
        reviews.sort(Comparator.comparing(StoreReview::getId).reversed());
        return reviews;
    }
}
