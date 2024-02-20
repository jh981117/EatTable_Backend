package com.lec.spring.service;


import com.lec.spring.domain.DTO.WaitingDto;
import com.lec.spring.domain.Partner;
import com.lec.spring.domain.TrueFalse;
import com.lec.spring.domain.User;
import com.lec.spring.domain.Waiting;
import com.lec.spring.repository.PartnerRepository;
import com.lec.spring.repository.UserRepository;
import com.lec.spring.repository.WaitingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class WaitingService {


    private final WaitingRepository waitingRepository;
    private final UserRepository userRepository;
    private final PartnerRepository partnerRepository;


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
    public Waiting saveWaiting (WaitingDto waitingDto, Long partnerId){


        User user = userRepository.findById(waitingDto.getUserId()).orElse(null);
        Partner partner = partnerRepository.findById(waitingDto.getPartnerId()).orElse(null);

        System.out.println(user);
        System.out.println(partner);
        Waiting waiting = Waiting.builder()
                .people(waitingDto.getPeople())
                .waitingState(waitingDto.getWaitingState().equals("True") ? TrueFalse.TRUE : TrueFalse.FALSE)
                .waitingRegDate(waitingDto.getWaitingRegDate())
                .user(user)
                .partner(partner)
                .build();
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

    public List<Waiting> findWaitingsByPartnerId(Long partnerId) {
        return waitingRepository.findByPartnerId(partnerId);
    }
}