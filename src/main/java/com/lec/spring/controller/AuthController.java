package com.lec.spring.controller;

import com.lec.spring.config.CustomUserDetailsService;
import com.lec.spring.config.JwtFilter;
import com.lec.spring.config.TokenProvider;
import com.lec.spring.domain.*;
import com.lec.spring.domain.DTO.*;
import com.lec.spring.repository.*;
import com.lec.spring.service.UserService;
import com.nimbusds.oauth2.sdk.token.RefreshToken;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

import static com.lec.spring.domain.RoleName.ROLE_MEMBER;

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
    private final CustomUserDetailsService customUserDetailsService;
    private final TokensRepository tokensRepository;
    private final BlackTokenRepository blackTokenRepository;
    private final RefreshTokensRepository refreshTokensRepository;




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
        String refreshToken = tokenProvider.createRefreshToken(authentication);

        HttpHeaders httpHeaders = new HttpHeaders();
        // response header에 jwt token에 넣어줌
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        httpHeaders.add("Refresh-Token", refreshToken); // 리프레시 토큰을 응답 헤더에 추가


        // 액세스 토큰과 리프레시 토큰을 모두 클라이언트에 전달
// authenticate 메소드 내


        // 기존의 리프레시 토큰 조회 및 업데이트 또는 생성
        RefreshTokens existingToken = refreshTokensRepository.findByUserId(user.getId());
        if (existingToken != null) {
            // 기존 토큰이 있다면, 리프레시 토큰 업데이트
            existingToken.setRefreshToken(refreshToken);
            tokensRepository.save(existingToken);
        } else {
            // 기존 토큰이 없다면, 새로운 토큰 생성 및 저장
            RefreshTokens newToken = new RefreshTokens();
            newToken.setRefreshToken(refreshToken);
            newToken.setUser(user);
            tokensRepository.save(newToken);
        }


        // 로그인 기록 저장
        UserHistory userHistory = new UserHistory();
        userHistory.setName(String.format("%s가 로그인을 하였습니다.", user.getUsername()));
        userHistoryRepository.save(userHistory);
        System.out.println(jwt + "123213131231");
        return new ResponseEntity<>(new TokensDto(jwt,refreshToken) , httpHeaders, HttpStatus.OK);
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

        BlackToken blackToken = new BlackToken();
        blackToken.setBlackToken(jwtToken);

        blackTokenRepository.save(blackToken);
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

        // 사용자의 권한 가져오기 ( 사용자에게 "ROLE_USER" 권한을 부여)
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






    //history 완료
    @PostMapping("/user/isPassword")
    public ResponseEntity<?> isPassword(@Valid @RequestBody PasswordRequest request) {
        boolean isAuthenticated = userService.authenticateUser(request.getUsername(), request.getPassword());
        if (!isAuthenticated) {
            // 인증 실패 시, 401 Unauthorized 반환
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("message", "비밀번호 틀림"));
        }

        // 인증 성공 시, 사용자 상태를 FALSE로 변경
        boolean isUpdated = userService.updateUserStateToFalse(request.getUsername());

        User user = userRepository.findByUsername(request.getUsername());
        UserHistory userHistory = new UserHistory();
        userHistory.setName(String.format("%s가 회원탈퇴를 하였습니다.", user.getUsername()));
        userHistoryRepository.save(userHistory);

        if (!isUpdated) {
            // 상태 업데이트 실패 시 (예: 사용자가 존재하지 않는 경우)
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", "사용자 상태 업데이트 실패"));
        }

        // 상태 업데이트 성공 시, 200 OK 반환
        return ResponseEntity.ok(Collections.singletonMap("message", "비밀번호 맞음, 사용자 상태 업데이트 성공"));
    }



    /// 리프레쉬 토큰으로 액세스 토큰 재발행하는 엔드포인트
    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshAccessToken(HttpServletRequest request) {
        String expiredAccessToken = request.getHeader("Authorization");
        if (expiredAccessToken == null || !expiredAccessToken.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid access token.");
        }

        // "Bearer " 접두사 제거
        expiredAccessToken = expiredAccessToken.substring(7);

        String username = null;
//        try {
            // 만료된 엑세스 토큰에서 사용자 이름 추출
            username = tokenProvider.getUsernameFromExpiredToken(expiredAccessToken);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid access token.");
//        }
        System.out.println(username);

        // 데이터베이스에서 사용자 찾기
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }

        // 데이터베이스에서 리프레쉬 토큰 찾기
        RefreshTokens refreshToken = refreshTokensRepository.findByUserId(user.getId());
        if (refreshToken == null || !tokenProvider.validateToken(refreshToken.getRefreshToken())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired refresh token.");
        }

        // 권한 정보 추출
        Collection<? extends GrantedAuthority> authorities =
                user.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getRoleName().name()))
                        .collect(Collectors.toList());

// 새 액세스 토큰 생성
        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUsername(), null, authorities);
        System.out.println(authentication + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        String newAccessToken = tokenProvider.createToken(authentication);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + newAccessToken);
        System.out.println(newAccessToken);
        return new ResponseEntity<>(new TokensDto(newAccessToken, refreshToken.getToken()), httpHeaders, HttpStatus.OK);


    }






        @PostMapping("/black")
    public ResponseEntity<?> black(@RequestBody BlackToken blackToken) {
        try {
            // Extract the token string from the BlackToken entity
            String tokenString = blackToken.getBlackToken();

            // Check if the token string exists in the repository
            boolean exists = blackTokenRepository.existsByBlackToken(tokenString);
            System.out.println(exists + "123444===============================================4");

            // Return the existence result
            return ResponseEntity.ok(exists);
        } catch (Exception e) {
            // Log the error
            e.printStackTrace();

            // Return an error response to the client
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }


}

