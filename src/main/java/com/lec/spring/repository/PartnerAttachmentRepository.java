package com.lec.spring.repository;

import com.lec.spring.domain.PartnerAttachment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PartnerAttachmentRepository extends JpaRepository<PartnerAttachment, Long> {

    List<PartnerAttachment> findByPartnerId(Long partnerId);

}
