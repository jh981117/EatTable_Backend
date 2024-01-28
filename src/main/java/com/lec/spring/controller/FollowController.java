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

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/follow")
public class FollowController {


    private final FollowService followService;


    //해당 사용자 팔로우 리스트보기
    @Transactional(readOnly = true)
    @GetMapping("list/{user}")
    public ResponseEntity<List<Follow>> userList(@PathVariable User user){
        return new ResponseEntity<>(followService.list(user),HttpStatus.OK);
    }



    //팔로우 등록
    @Transactional
    @PostMapping("/insert")
    public ResponseEntity<?> insert (@RequestBody Follow follow){
        return  new ResponseEntity<>(followService.insert(follow),HttpStatus.CREATED);
    }


    //팔로우 삭제
    @Transactional
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        return new ResponseEntity<>(followService.delete(id),HttpStatus.OK);
    }

}
