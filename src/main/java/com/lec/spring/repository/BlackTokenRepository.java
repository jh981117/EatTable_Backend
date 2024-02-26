package com.lec.spring.repository;

import com.lec.spring.domain.BlackToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlackTokenRepository extends JpaRepository<BlackToken, Long> {
    // Corrected to check existence by token string
    boolean existsByBlackToken(String token);
}
