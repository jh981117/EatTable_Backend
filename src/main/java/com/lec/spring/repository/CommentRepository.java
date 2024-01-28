package com.lec.spring.repository;

import com.lec.spring.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {


    List<Comment> findByStoreReviewId(Long storeReviewId);

    @Modifying
    @Transactional
    @Query("DELETE FROM comment c WHERE c.id = ?1")
    int deleteByIdint(Long id);
}
