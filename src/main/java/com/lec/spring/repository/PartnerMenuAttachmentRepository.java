package com.lec.spring.repository;

import com.lec.spring.domain.PartnerMenuAttachment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PartnerMenuAttachmentRepository extends JpaRepository<PartnerMenuAttachment, Long> {


        List<PartnerMenuAttachment> findByPartnerMenuId(Long partnerMenuId);


}
