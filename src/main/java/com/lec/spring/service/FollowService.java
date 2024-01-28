package com.lec.spring.service;

import com.lec.spring.domain.Follow;
import com.lec.spring.domain.User;
import com.lec.spring.repository.FollowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class FollowService {


    private final FollowRepository followRepository;


    //해당유저의 follow

    @Transactional
    public List<Follow> list(User user){
        return followRepository.findAllByFromId(user);
    }

    //팔로우 등록
    @Transactional
    public Follow insert(Follow follow){
        return followRepository.save(follow);
    }


    //삭제되면 1  아니면 0
    @Transactional
    public String delete(Long id){
        Follow followDelete = followRepository.findById(id).orElse(null);
        if(followDelete != null){
            followRepository.deleteById(id);
            return "1";
        }
                return "0";
    }



}
