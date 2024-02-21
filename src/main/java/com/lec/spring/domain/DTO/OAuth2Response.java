package com.lec.spring.domain.DTO;

public interface OAuth2Response {
    //제공자
    String getProvider();

    //제공자가 발급해주는 아이디
    String getProviderId();

    // 이메일
    String getEmail();

    //사용자명
    String getName();

    String getBirthday();

    String getNickName();

    String getPhone();
}
