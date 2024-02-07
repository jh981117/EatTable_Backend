package com.lec.spring.service;


import com.lec.spring.domain.TrueFalse;
import com.lec.spring.domain.Waiting;
import com.lec.spring.repository.WaitingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class WaitingService {


    private final WaitingRepository waitingRepository;

    @Transactional
    public List<Waiting> findAllWaitings() {
        return waitingRepository.findAll();
    }

//    @Transactional
//    public Waiting findWaitingById(Long id) {
//        return waitingRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Waiting not found with id: " + id));
//    }

    @Transactional
    public Waiting saveWaiting (Waiting waiting){
        return waitingRepository.save(waiting);
    }

    @Transactional
    public Waiting updateWaiting(Long id, TrueFalse newWaitingState) {
        Waiting waiting = waitingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Waiting not found with id: " + id));

        waiting.setWaitingState(newWaitingState);
        return waitingRepository.save(waiting);
    }

    @Transactional
    public void deleteByUserId(Long userId) {
        waitingRepository.deleteByUserId(userId);
    }

    // 유저 개인의 웨이팅 찾기
    @Transactional
    public List<Waiting> findUserWaiting(Long userId) {
        return waitingRepository.findByUserId(userId);
    }
}