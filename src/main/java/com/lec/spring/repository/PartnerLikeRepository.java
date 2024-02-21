package com.lec.spring.repository;

import com.lec.spring.domain.PartnerLike;
import com.lec.spring.domain.PartnerLikeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface PartnerLikeRepository extends JpaRepository<PartnerLike , PartnerLikeId> {
    Optional<PartnerLike> findById(PartnerLikeId id);
    boolean existsById(PartnerLikeId id);

    @Query("SELECT COUNT(pl) FROM PartnerLike pl WHERE pl.partnerId.id = :partnerId")
    long countByPartnerId(@Param("partnerId") Long partnerId);
}
