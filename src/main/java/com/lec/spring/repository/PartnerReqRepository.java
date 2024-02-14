package com.lec.spring.repository;

import com.lec.spring.domain.PartnerReq;
import com.lec.spring.domain.PartnerReqState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PartnerReqRepository extends JpaRepository<PartnerReq,Long> {


//    Page<PartnerReq> findByPartnerReqState(String state,Pageable pageable);

//    List<PartnerReq> findByPartnerReqState(PartnerReqState partnerReqState);

}
