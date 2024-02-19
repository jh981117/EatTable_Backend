package com.lec.spring.repository;


import com.lec.spring.domain.DTO.PartnerDto;
import com.lec.spring.domain.Partner;
import com.lec.spring.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PartnerRepository extends JpaRepository<Partner,Long> {


    @Query("SELECT e FROM Partner e WHERE e.storeName LIKE %:keyword% OR e.partnerName LIKE %:keyword%")
    Page<Partner> findByKeyword(@Param("keyword") String keyword, Pageable pageable);


    List<Partner> findByUserId(Long userId);

    List<Partner> findByUser(User user);

    @Query("SELECT e FROM Partner e WHERE e.storeName LIKE %:keyword% OR e.address.area LIKE %:keyword%")
    List<Partner> search(@Param("keyword") String keyword);


    @Query("SELECT AVG(r.avg) FROM StoreReview r WHERE r.partner.id = ?1")
    Double findAvhPartner(Long partnerId);
}
