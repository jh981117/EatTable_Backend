package com.lec.spring.repository;

import com.lec.spring.domain.StoreReview;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreReviewRepository extends JpaRepository<StoreReview, Long> {



    List<StoreReview> findByPartnerId(Long partnerId);
}
