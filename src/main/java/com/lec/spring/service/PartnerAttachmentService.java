package com.lec.spring.service;

import com.lec.spring.domain.PartnerAttachment;
import com.lec.spring.repository.PartnerAttachmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PartnerAttachmentService {

    private final PartnerAttachmentRepository partnerAttachmentRepository;

    @Transactional
    public PartnerAttachment findById(Long id){
        return partnerAttachmentRepository.findById(id).orElse(null);
    }

}
