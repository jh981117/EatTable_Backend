package com.lec.spring.repository;

import com.lec.spring.domain.PartnerLike;
import com.lec.spring.domain.PartnerLikeId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PartnerLikeRepository extends JpaRepository<PartnerLike , PartnerLikeId> {
    Optional<PartnerLike> findById(PartnerLikeId id);
    boolean existsById(PartnerLikeId id);
}
