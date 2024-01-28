package com.lec.spring.service;


import com.lec.spring.repository.WaitingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class WaitingService {


    private final WaitingRepository waitingRepository;
}
