package com.lec.spring.repository;

import com.lec.spring.domain.PartnerReviewAttachment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PartnerReviewAttachmentRepository extends JpaRepository<PartnerReviewAttachment, Long> {




        List<PartnerReviewAttachment> findByStoreReviewId(Long storeReviewId);


    long countByPartnerId(Long partnerId);

    long deleteByStoreReviewId(Long storeReviewId);
}
