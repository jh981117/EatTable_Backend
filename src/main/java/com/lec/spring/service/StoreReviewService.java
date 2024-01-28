package com.lec.spring.service;


import com.lec.spring.domain.StoreReview;
import com.lec.spring.repository.StoreReviewRepository;
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


    //매장리뷰작성
    @Transactional
    public StoreReview write(StoreReview storeReview, List<MultipartFile> img){
    return null;
    }


    //리뷰디테일 ( ?? 필요안할수도)
    @Transactional
    public Optional<StoreReview> detail (Long reviewId){
        return storeReviewRepository.findById(reviewId);
    }
}
