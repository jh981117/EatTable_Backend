package com.lec.spring.service;

import com.lec.spring.domain.PartnerReviewAttachment;
import com.lec.spring.repository.PartnerReviewAttachmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PartnerReviewAttachmentService {

    private final PartnerReviewAttachmentRepository partnerReviewAttachmentRepository;

    public PartnerReviewAttachment findById(Long id) {
        return partnerReviewAttachmentRepository.findById(id).orElse(null);
    }

}
