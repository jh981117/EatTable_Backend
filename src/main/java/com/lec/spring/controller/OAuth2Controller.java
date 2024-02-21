package com.lec.spring.controller;

import com.lec.spring.config.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("/oauth2")
public class OAuth2Controller {

    private final TokenProvider tokenProvider;
    private final OAuth2UserService oAuth2UserService;

    @PostMapping("/naverlogin")
    public ResponseEntity<?> receiveToken(@RequestHeader("Authorization") String authorizationHeader) {
        // Authorization 헤더에서 AccessToken 추출
        String accessToken = authorizationHeader.substring("Bearer ".length());

        // 네이버 사용자 정보 엔드포인트 URL
        String profileUrl = "https://openapi.naver.com/v1/nid/me";

        try {
            // 사용자 정보 요청
            String responseBody = sendGetRequest(profileUrl, accessToken);
            System.out.println("리스폰스바디"+responseBody); // 응답 확인용. 실제로는 파싱하여 사용할 것

            // 클라이언트에게 응답 반환
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            e.printStackTrace();
            // 오류 발생 시 클라이언트에게 500 에러 반환
            return ResponseEntity.status(500).build();
        }
    }

    // HTTP GET 요청을 보내는 메서드
    private String sendGetRequest(String url, String accessToken) throws IOException {
        URL apiUrl = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", "Bearer " + accessToken);

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response.toString();
        } else {
            throw new IOException("Failed to fetch user profile. HTTP error code: " + responseCode);
        }
    }
}
