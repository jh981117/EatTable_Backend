package com.lec.spring.controller;



import com.lec.spring.config.CustomUserDetails;
import com.lec.spring.domain.DTO.PartnerWriteDto;
import com.lec.spring.domain.Partner;
import com.lec.spring.domain.User;
import com.lec.spring.domain.UserHistory;
import com.lec.spring.repository.UserHistoryRepository;
import com.lec.spring.repository.UserRepository;
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
        private final UserRepository userRepository;
        private final UserHistoryRepository userHistoryRepository;

        @GetMapping("/list")
        public ResponseEntity<?> list() {
                return new ResponseEntity<>(userService.list(), HttpStatus.OK);
        }

        //history완료
        @PostMapping("/signup")
        public ResponseEntity<?> signup(@RequestBody User user) {
                ResponseEntity<?> responseEntity = new ResponseEntity<>(userService.signup(user), HttpStatus.CREATED);

                UserHistory userHistory = new UserHistory();
                userHistory.setName(String.format("%s님이 %s 아이디로 회원가입하였습니다.", user.getName(), user.getNickName()));
                userHistoryRepository.save(userHistory);

                return responseEntity;
        }


        @GetMapping("/mypage/{id}")
        public ResponseEntity<?> detail(@PathVariable Long id) {
                return new ResponseEntity<>(userService.detail(id), HttpStatus.OK);
        }

        //history완료
        @PutMapping("/update")
        public ResponseEntity<?> update(@RequestBody User user) {

                User userInfo = userRepository.findById(user.getId()).orElse(null);
                UserHistory userHistory = new UserHistory();

                if (!user.getNickName().equals(userInfo.getNickName())) {
                        userHistory.setName(String.format("%s님이 회원 정보 닉네임을 수정하였습니다.", user.getUsername() ));
                        userHistoryRepository.save(userHistory);
                } else if (!user.getPhone().equals(userInfo.getPhone())) {
                        userHistory.setName(String.format("%s님이 회원 정보 전화번호를 수정하였습니다.", user.getUsername() ));
                        userHistoryRepository.save(userHistory);
                }

                ResponseEntity<?> responseEntity = new ResponseEntity<>(userService.update(user), HttpStatus.OK);

                return responseEntity;
        }

        // 아직 history 대기중
        @DeleteMapping("/userout/{id}")
        public ResponseEntity<?> delete(@PathVariable Long id) {

//                User user = userRepository.findById(id).orElse(null);
//
//                UserHistory userHistory = new UserHistory();
//                userHistory.setName(String.format("%s님이 회원 탈퇴를 하였습니다.", user.getNickName()));
//                userHistoryRepository.save(userHistory);

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
