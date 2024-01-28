package com.lec.spring.service;

import com.lec.spring.domain.PartnerMenuAttachment;
import com.lec.spring.repository.PartnerMenuAttachmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PartnerMenuAttachmentService {

    private final PartnerMenuAttachmentRepository partnerMenuAttachmentRepository;


    public PartnerMenuAttachment findById(Long id){
        return partnerMenuAttachmentRepository.findById(id).orElse(null);
    }

}
