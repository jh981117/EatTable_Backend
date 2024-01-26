package com.lec.spring.repository;

import com.lec.spring.domain.Waiting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WaitingRepository extends JpaRepository<Waiting , Long> {
}
