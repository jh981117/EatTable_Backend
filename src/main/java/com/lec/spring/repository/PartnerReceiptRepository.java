package com.lec.spring.repository;

import com.lec.spring.domain.PartnerReceipt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartnerReceiptRepository extends JpaRepository<PartnerReceipt,Long> {
}
