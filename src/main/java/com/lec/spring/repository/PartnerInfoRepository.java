package com.lec.spring.repository;

import com.lec.spring.domain.PartnerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartnerInfoRepository extends JpaRepository<PartnerInfo,Long> {
}
