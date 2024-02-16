package com.lec.spring.repository;

import com.lec.spring.domain.PartnerReq;
import com.lec.spring.domain.PartnerReqState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PartnerReqRepository extends JpaRepository<PartnerReq,Long> {


//    Page<PartnerReq> findByPartnerReqState(String state,Pageable pageable);

//    List<PartnerReq> findByPartnerReqState(PartnerReqState partnerReqState);

}
