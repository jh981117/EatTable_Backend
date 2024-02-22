package com.lec.spring.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lec.spring.domain.CustomOAuth2User;
import com.lec.spring.domain.DTO.UserDto;
import com.lec.spring.domain.Role;
import com.lec.spring.domain.RoleName;
import com.lec.spring.domain.User;
import com.lec.spring.repository.RoleRepository;
import com.lec.spring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;

@RequiredArgsConstructor
@Service
public class NaverOAuth2UserService extends DefaultOAuth2UserService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Value("${app.oauth2.password}")
    private String oauth2Password;


    public OAuth2User loadUser(String accessToken) throws OAuth2AuthenticationException, IOException {

        // 네이버 사용자 정보 엔드포인트 URL
        String profileUrl = "https://openapi.naver.com/v1/nid/me";

        // 사용자 정보 요청
        String responseBody = sendGetRequest(profileUrl, accessToken);

        // JSON 파싱을 위한 ObjectMapper 객체 생성
        ObjectMapper objectMapper = new ObjectMapper();

        // JSON 문자열을 JsonNode로 변환
        JsonNode jsonNode = objectMapper.readTree(responseBody);

        // 사용자 정보 추출
        String name = jsonNode.get("response").get("name").asText();
        String email = jsonNode.get("response").get("email").asText();
        String username = "naver" + jsonNode.get("response").get("id").asText();
        String birthday = jsonNode.get("response").get("birthyear").asText().substring(2)
                + jsonNode.get("response").get("birthday").asText().replace("-", "");
        String nickname = jsonNode.get("response").get("nickname").asText();
        String provider = "naver";
        String providerId = jsonNode.get("response").get("id").asText();
        String phone = jsonNode.get("response").get("mobile").asText();
        Role role = roleRepository.findByRoleName(RoleName.ROLE_MEMBER);
        User userInfo = userRepository.findByUsername(username);

        if (userInfo == null) {
            User user = User.builder()
                    .username(username)
                    .email(email)
                    .name(name)
                    .birthdate(birthday)
                    .nickName(nickname)
                    .password(oauth2Password)
                    .phone(phone)
                    .roles(Collections.singleton(role))
                    .oauth(provider)
                    .oauthId(providerId)
                    .activated(true)
                    .build();

            userRepository.save(user);

            return new CustomOAuth2User(user);
        } else {

            userInfo.setEmail(email);
            userInfo.setName(name);

            userRepository.save(userInfo);

            return new CustomOAuth2User(userInfo);
        }
    }

    private String sendGetRequest(String url, String accessToken) throws IOException {
        URL apiUrl = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", "Bearer " + accessToken);

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
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
