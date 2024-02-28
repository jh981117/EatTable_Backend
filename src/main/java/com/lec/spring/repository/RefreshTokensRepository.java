package com.lec.spring.repository;

import com.lec.spring.domain.RefreshTokens;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokensRepository extends JpaRepository<RefreshTokens, Long> {
    RefreshTokens findByUserId(Long userId);
}
