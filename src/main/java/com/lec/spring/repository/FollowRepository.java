package com.lec.spring.repository;

import com.lec.spring.domain.Follow;
import com.lec.spring.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow,Long> {

    List<Follow> findAllByFromId(User userId);
}
