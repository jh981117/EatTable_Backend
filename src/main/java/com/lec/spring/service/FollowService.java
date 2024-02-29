package com.lec.spring.service;

import com.lec.spring.domain.Follow;
import com.lec.spring.domain.FollowId;
import com.lec.spring.domain.User;
import com.lec.spring.repository.FollowRepository;
import com.lec.spring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class FollowService {


    private final FollowRepository followRepository;
    private final UserRepository userRepository;





    //팔로우 등록
    @Transactional
    public Follow createFollow(Long fromUserId, Long toUserId) {
        User fromUser = userRepository.findById(fromUserId)
                .orElse(null);
        User toUser = userRepository.findById(toUserId)
                .orElse(null);

        FollowId followId = new FollowId(fromUserId, toUserId);
        Follow follow = new Follow(followId, fromUser, toUser);
        return followRepository.save(follow);
    }


    //삭제되면 1 아니면 0
//삭제되면 1 아니면 0
    @Transactional
    public void deleteFollow(Long fromUserId, Long toUserId) {
        followRepository.deleteByFromIdAndToId(fromUserId, toUserId);
    }


    @Transactional
    public List<Follow> getFollowingList(Long userId) {
        return followRepository.findByFromUserId(userId);
    }

    // 팔로우 상태 확인
    public boolean checkFollowStatus(Long fromId, Long toId) {
        return followRepository.existsByIdFromIdAndIdToId(fromId, toId);
    }


    public List<Follow> findFollow(Long userId) {
        return followRepository.findByFromUserId(userId);
    }
}
