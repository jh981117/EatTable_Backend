package com.lec.spring.controller;


import com.lec.spring.domain.User;

import com.lec.spring.repository.UserRepository;
import com.lec.spring.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/attachments")
@RequiredArgsConstructor
@CrossOrigin
public class AttachmentController {

    private final S3Service s3Service;
    private final UserRepository userRepository;





    @PutMapping("/update-image")
    public ResponseEntity<?> updateImage(@RequestParam("file") MultipartFile file) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentUserName = authentication.getName();
            System.out.println(currentUserName);
            User user = userRepository.findByUsername(currentUserName);

            if (user == null) {
                return ResponseEntity.badRequest().body("사용자를 찾을 수 없습니다.");
            }

            // 새 이미지를 S3에 업로드하고 URL을 받음
            String s3StoragePath = s3Service.uploadFile(file);
            System.out.println("파일"+s3StoragePath);

            // 기존 이미지 정보를 찾아 업데이트
            System.out.println("유저"+user);

                // 새 이미지 URL로 업데이트
                user.setProfileImageUrl(s3StoragePath);

            userRepository.save(user);

            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("이미지 업데이트 중 오류 발생: " + e.getMessage());
        }
    }


}

