package com.lec.spring.repository;

import com.lec.spring.domain.RefreshTokens;
import com.lec.spring.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokensRepository extends JpaRepository<RefreshTokens,Long> {
    Optional<User> findByRefreshToken(String refreshToken);
}
