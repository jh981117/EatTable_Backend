package com.lec.spring.repository;


import com.lec.spring.domain.Partner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PartnerRepository extends JpaRepository<Partner,Long> {


    @Query("SELECT e FROM Partner e WHERE e.storeName LIKE %:keyword% OR e.partnerName LIKE %:keyword%")
    Page<Partner> findByKeyword(@Param("keyword") String keyword, Pageable pageable);




    List<Partner> findByUserId(Long userId);
}
