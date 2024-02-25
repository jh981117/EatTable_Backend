package com.lec.spring.repository;

import com.lec.spring.domain.DTO.StoreReviewDto;
import com.lec.spring.domain.StoreReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface StoreReviewRepository extends JpaRepository<StoreReview, Long> {



    List<StoreReview> findByPartnerId(Long partnerId ,Sort sort);

    long countByPartnerId(Long partnerId);

    // partnerId에 해당하는 모든 리뷰의 평점 평균을 계산하는 쿼리

    List<StoreReview> findByUserId(Long userId);
    Page<StoreReview> findByUserId(Pageable pageable, Long userId);



    @Query("SELECT r FROM StoreReview r WHERE r.user.id IN (SELECT f.toUser.id FROM Follow f WHERE f.fromUser.id = :userId)")
    Page<StoreReview> findAllByFollowing(Long userId, Pageable pageable);


}
