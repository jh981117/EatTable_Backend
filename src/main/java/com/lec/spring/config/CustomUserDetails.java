package com.lec.spring.config;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class CustomUserDetails extends org.springframework.security.core.userdetails.User {
    private static final long serialVersionUID = 1L;

    private final String nickname; // 추가적인 사용자 정보
    private final Long id; // 추가적인 사용자 정보




    public CustomUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities, String nickname ,Long id ) {
        super(username, password, authorities);
        this.nickname = nickname;
        this.id = id;


    }

    // 닉네임 getter 추가
    public String getNickname() {
        return nickname;
    }

    public Long getId(){
        return id;
    }

    // 필요한 경우 여기에 추가적인 사용자 정보를 포함시킬 수 있습니다.
}

