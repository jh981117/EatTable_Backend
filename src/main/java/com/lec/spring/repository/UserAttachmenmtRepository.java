package com.lec.spring.repository;

import com.lec.spring.domain.UserAttachment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAttachmenmtRepository extends JpaRepository<UserAttachment,Long> {
}
