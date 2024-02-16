package com.lec.spring.controller;



import com.lec.spring.config.CustomUserDetails;
import com.lec.spring.domain.User;
import com.lec.spring.service.UserService;
import com.lec.spring.util.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("/api/user")
public class UserController {


        private final UserService userService;


        @GetMapping("/list")
        public ResponseEntity<?> list() {
                return new ResponseEntity<>(userService.list(), HttpStatus.OK);
        }

        @PostMapping("/signup")
        public ResponseEntity<?> signup(@RequestBody User user) {
                return new ResponseEntity<>(userService.signup(user), HttpStatus.CREATED);
        }


        @GetMapping("/mypage/{id}")
        public ResponseEntity<?> detail(@PathVariable Long id) {
                return new ResponseEntity<>(userService.detail(id), HttpStatus.OK);
        }


        @PutMapping("/update")
        public ResponseEntity<?> update(@RequestBody User user) {
                return new ResponseEntity<>(userService.update(user), HttpStatus.OK);
        }

        @DeleteMapping("/userout/{id}")
        public ResponseEntity<?> delete(@PathVariable Long id) {
                return new ResponseEntity<>(userService.delete(id), HttpStatus.OK);
        }


        @GetMapping("/profile")
        @PreAuthorize("hasAnyRole('MEMBER','ADMIN')")
        public ResponseEntity<User> getMyUserInfo() {
                return ResponseEntity.ok(userService.getMyUserWithAuthorities().get());
        }

        @GetMapping("/{username}")
        @PreAuthorize("hasAnyRole('ADMIN')")
        public ResponseEntity<User> getUserInfo(@PathVariable String username) {
                return ResponseEntity.ok(userService.getUserWithAuthorities(username).get());
        }










//        @GetMapping("/profile")
//        public ResponseEntity<?> getUserProfile() {
//                // SecurityUtil을 사용하여 현재 사용자 정보를 가져옴
//                Optional<CustomUserDetails> userDetails = SecurityUtil.getCurrentUserDetails();
//
//                if (!userDetails.isPresent()) {
//                        // 사용자 정보가 없는 경우 오류 처리
//                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
//                }
//
//                // 데이터베이스에서 사용자 정보 조회 (예시)
//                User user = userService.getUserById(userDetails.get().getId());
//
//                // 사용자 정보를 응답으로 반환
//                return ResponseEntity.ok(user);
//        }


        @Autowired
        private PasswordEncoder passwordEncoder;


        @GetMapping("/pw")
        public String password (String pw) {
                return passwordEncoder.encode(pw);
        }


}
