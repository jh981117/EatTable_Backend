package com.lec.spring.repository;

import com.lec.spring.domain.Follow;
import com.lec.spring.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow,Long> {

    @Modifying
    @Query("DELETE FROM Follow f WHERE f.id.fromId = :fromId AND f.id.toId = :toId")
    @Transactional
    void deleteByFromIdAndToId(@Param("fromId") Long fromId, @Param("toId") Long toId);


    boolean existsByIdFromIdAndIdToId(Long fromId, Long toId);
    List<Follow> findByFromUserId(Long userId);
}
