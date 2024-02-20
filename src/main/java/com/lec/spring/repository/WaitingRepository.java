package com.lec.spring.repository;

import com.lec.spring.domain.Waiting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WaitingRepository extends JpaRepository<Waiting , Long> {
    List<Waiting> findByUserId(Long userId);

    void deleteByUserId(Long userId);

    List<Waiting> findByPartnerId(Long partnerId);
}
