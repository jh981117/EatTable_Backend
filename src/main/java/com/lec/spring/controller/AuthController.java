package com.lec.spring.controller;

import com.lec.spring.config.JwtFilter;
import com.lec.spring.config.TokenProvider;
import com.lec.spring.domain.DTO.LoginDto;
import com.lec.spring.domain.DTO.PasswordChangeRequest;
import com.lec.spring.domain.DTO.PasswordRequest;
import com.lec.spring.domain.DTO.TokenDto;
import com.lec.spring.domain.User;
import com.lec.spring.domain.UserHistory;
import com.lec.spring.repository.UserHistoryRepository;
import com.lec.spring.repository.UserRepository;
import com.lec.spring.service.UserService;
import com.lec.spring.util.U;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin
public class AuthController {
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserService userService;
    private final UserRepository userRepository;
    private final UserHistoryRepository userHistoryRepository;



    //history 완료
    @PostMapping("/authenticate")
    public ResponseEntity<?> authorize(@Valid @RequestBody LoginDto loginDto) {
        User user = userRepository.findByUsername(loginDto.getUsername());
        if (user == null) {
            // 아이디가 존재하지 않는 경우
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("message", "아이디가 존재하지 않습니다."));
        }
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

        // authenticate 메소드가 실행될 때 CustomUserDetailsService class의 loadUserByUsername 메소드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        // 해당 객체를 SecurityContextHolder에 저장하고
        SecurityContextHolder.getContext().setAuthentication(authentication);


        if (!user.isActivated()) {
            // 탈퇴한 회원인 경우
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Collections.singletonMap("message", "탈퇴한 회원입니다."));
        }

        // authentication 객체를 createToken 메소드를 통해서 JWT Token을 생성
        String jwt = tokenProvider.createToken(authentication);

        HttpHeaders httpHeaders = new HttpHeaders();
        // response header에 jwt token에 넣어줌
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        // 로그인 기록 저장
        UserHistory userHistory = new UserHistory();
        userHistory.setName(String.format("%s가 로그인을 하였습니다.", user.getUsername()));
        userHistoryRepository.save(userHistory);

        return new ResponseEntity<>(new TokenDto(jwt), httpHeaders, HttpStatus.OK);
    }
    //history 완료
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String token) {
        String jwtToken = token.substring(7); // "Bearer " 부분을 제외한 토큰 추출

        // 토큰을 이용하여 사용자 인증 객체를 가져옴
        UsernamePasswordAuthenticationToken authenticationToken = tokenProvider.getAuthentication(jwtToken);
        String username = authenticationToken.getName();

        User user = userRepository.findByUsername(username);

        UserHistory userHistory = new UserHistory();
        userHistory.setName(String.format("%s님이 로그아웃을 하였습니다.", user.getUsername()));
        userHistoryRepository.save(userHistory);

        return ResponseEntity.ok("로그아웃 되었습니다.");
    }


    //history 완료
    @PostMapping("/user/change-password")
    public ResponseEntity<?> changePassword(@Valid @RequestBody PasswordChangeRequest request) {
        boolean isAuthenticated = userService.authenticateUser(request.getUsername(), request.getOldPassword());
        if (!isAuthenticated) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid old password");
        }

        // 비밀번호 업데이트
        userService.updatePassword(request.getUsername(), request.getNewPassword());

        // 사용자의 권한 가져오기 (예제로 사용자에게 "ROLE_USER" 권한을 부여)
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));

        // 새 Authentication 객체 생성
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                request.getUsername(), null, authorities);

        // 새 JWT 토큰 생성
        String newToken = tokenProvider.createToken(authentication);
        System.out.println(newToken);

        ResponseEntity<?> responseEntity = ResponseEntity.ok(new TokenDto(newToken));

        User user = userRepository.findByUsername(request.getUsername());
        UserHistory userHistory = new UserHistory();
        userHistory.setName(String.format("%s가 비밀번호를 변경하였습니다.", user.getUsername()));
        userHistoryRepository.save(userHistory);

        return responseEntity; // TokenDto는 토큰을 담아 반환할 DTO
    }







    @PostMapping("/user/isPassword")
    public ResponseEntity<?> isPassword(@Valid @RequestBody PasswordRequest request) {
        boolean isAuthenticated = userService.authenticateUser(request.getUsername(), request.getPassword());
        if (!isAuthenticated) {
            // 인증 실패 시, 401 Unauthorized 반환
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("message", "비밀번호 틀림"));
        }

        // 인증 성공 시, 사용자 상태를 FALSE로 변경
        boolean isUpdated = userService.updateUserStateToFalse(request.getUsername());
        if (!isUpdated) {
            // 상태 업데이트 실패 시 (예: 사용자가 존재하지 않는 경우)
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", "사용자 상태 업데이트 실패"));
        }

        // 상태 업데이트 성공 시, 200 OK 반환
        return ResponseEntity.ok(Collections.singletonMap("message", "비밀번호 맞음, 사용자 상태 업데이트 성공"));
    }



}

