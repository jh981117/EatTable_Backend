package com.lec.spring.repository;

import com.lec.spring.domain.StoreReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreReviewRepository extends JpaRepository<StoreReview, Long> {
}
