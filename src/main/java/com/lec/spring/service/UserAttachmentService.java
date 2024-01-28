package com.lec.spring.service;

import com.lec.spring.domain.UserAttachment;
import com.lec.spring.repository.UserAttachmenmtRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserAttachmentService {

    private final UserAttachmenmtRepository userAttachmenmtRepository;




    public UserAttachment findById(Long id) {
        return userAttachmenmtRepository.findById(id).orElse(null);
    }
}
