package com.lec.spring.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lec.spring.config.JwtFilter;
import com.lec.spring.config.TokenProvider;
import com.lec.spring.domain.DTO.TokenDto;
import com.lec.spring.domain.DTO.UserDto;
import com.lec.spring.domain.Role;
import com.lec.spring.domain.RoleName;
import com.lec.spring.domain.User;
import com.lec.spring.domain.UserHistory;
import com.lec.spring.repository.RoleRepository;
import com.lec.spring.repository.UserHistoryRepository;
import com.lec.spring.repository.UserRepository;
import com.lec.spring.service.NaverOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;

@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("/oauth2")
public class OAuth2Controller {

    private final TokenProvider tokenProvider;
    private final OAuth2UserService oAuth2UserService;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final NaverOAuth2UserService naverOAuth2UserService;
    private final UserHistoryRepository userHistoryRepository;

    @PostMapping("/naverlogin")
    public ResponseEntity<?> naverlogin(@RequestHeader("Authorization") String authorizationHeader) throws IOException {

        // Authorization 헤더에서 AccessToken 추출
        String accessToken = authorizationHeader.substring("Bearer ".length());

        OAuth2User oAuth2User = naverOAuth2UserService.loadUser(accessToken);
    // 사용자 정보를 기반으로 UsernamePasswordAuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(oAuth2User, null, oAuth2User.getAuthorities());

    // JWT 토큰 생성
        String jwt = tokenProvider.createToken(authenticationToken);

        HttpHeaders httpHeaders = new HttpHeaders();
        // response header에 jwt token에 넣어줌
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        System.out.println(oAuth2User.getName());
        User user = userRepository.findByUsername(oAuth2User.getName());
        UserHistory userHistory = new UserHistory();
        userHistory.setName(String.format("%s가 로그인을 하였습니다.", user.getUsername()));
        userHistoryRepository.save(userHistory);

        return new ResponseEntity<>(new TokenDto(jwt), httpHeaders, HttpStatus.OK);


    }

}
