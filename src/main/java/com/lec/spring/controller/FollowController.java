package com.lec.spring.controller;


import com.lec.spring.domain.Follow;
import com.lec.spring.domain.User;
import com.lec.spring.repository.FollowRepository;
import com.lec.spring.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/follow")
@CrossOrigin
public class FollowController {


    private final FollowService followService;


    //해당 사용자 팔로우 리스트보기



    //팔로우 등록
    @PostMapping("/{fromId}/{toId}")
    public ResponseEntity<?> createFollow(@PathVariable Long fromId, @PathVariable Long toId) {
        Follow follow = followService.createFollow(fromId, toId);
        return ResponseEntity.ok(follow);
    }



    //팔로우 삭제
    @DeleteMapping("/{fromUserId}/{toUserId}")
    public ResponseEntity<String> deleteFollow(@PathVariable Long fromUserId, @PathVariable Long toUserId) {
        followService.deleteFollow(fromUserId, toUserId);
        return ResponseEntity.ok("성공");
    }








    //로그인중인 유저의 팔로우상태
    @GetMapping("/{userId}/following")
    public ResponseEntity<List<Follow>> getFollowingList(@PathVariable Long userId) {
        System.out.println(userId);
        List<Follow> followingList = followService.getFollowingList(userId);
        return ResponseEntity.ok(followingList);

    }


    // 팔로우 상태 조회
    @GetMapping("/status/{userId}/{toUserId}")
    public ResponseEntity<?> getFollowStatus(@PathVariable Long userId, @PathVariable Long toUserId) {
        boolean isFollowing = followService.checkFollowStatus(userId, toUserId);
        return ResponseEntity.ok().body(Map.of("isFollowing", isFollowing));
    }


}
