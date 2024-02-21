package com.lec.spring.repository;

import com.lec.spring.domain.DTO.StoreReviewDto;
import com.lec.spring.domain.StoreReview;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StoreReviewRepository extends JpaRepository<StoreReview, Long> {



    List<StoreReview> findByPartnerId(Long partnerId);

    long countByPartnerId(Long partnerId);

    // partnerId에 해당하는 모든 리뷰의 평점 평균을 계산하는 쿼리


    List<StoreReview> findByUserId(Long userId);

}
