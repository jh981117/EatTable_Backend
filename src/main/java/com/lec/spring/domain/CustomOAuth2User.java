package com.lec.spring.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.*;

public class CustomOAuth2User implements OAuth2User {
    private final User user; // 사용자 정보를 가지는 필드

    public CustomOAuth2User(User user) {
        this.user = user;
    }


    @Override
    public Map<String, Object> getAttributes() {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("id", user.getId());
        attributes.put("username", user.getUsername());
        attributes.put("password", user.getPassword());
        attributes.put("name", user.getName());
        attributes.put("nickName", user.getNickName());
        attributes.put("birthdate", user.getBirthdate());
        attributes.put("email", user.getEmail());
        attributes.put("phone", user.getPhone());
        attributes.put("activated", user.isActivated());
        attributes.put("temperature", user.getTemperature());
        attributes.put("profileImageUrl", user.getProfileImageUrl());
        attributes.put("bio", user.getBio());
        attributes.put("oauth", user.getOauth());
        attributes.put("oauthId", user.getOauthId());

        // Add additional attributes as needed

        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (Role role : user.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.getRoleName().toString()));
        }
        return authorities;
    }


    @Override
    public String getName() {
        // 사용자의 이름을 반환합니다.
        return user.getUsername();
    }
}
