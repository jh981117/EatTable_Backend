package com.lec.spring.controller;

import com.lec.spring.repository.UserHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("/api/userhistory")
public class UserHistoryController {
   private final UserHistoryRepository userHistoryRepository;
    @GetMapping("/totallist")
    public ResponseEntity<?> totallist(){
        return new ResponseEntity<>(userHistoryRepository.findAll(), HttpStatus.OK);
    }
}
