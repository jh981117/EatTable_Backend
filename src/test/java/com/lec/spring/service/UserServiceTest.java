package com.lec.spring.service;

import com.lec.spring.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;



@SpringBootTest

class UserServiceTest {


    @Autowired
    private UserService userService;


    @Test
    void list() {
        List<User> userlist = userService.list();
        System.out.println(userlist);
    }

    @Test
    void signup() {
        User user = User.builder()
                .username("user1")
                .password("1234")
                .name("송민호")
                .email("mino030@naver.com")
                .bio("123asd")
                .birthdate("910309")
                .nickName("송")
                .build();
        User cnt = userService.signup(user);
        System.out.println("register 결과: " + cnt + " : " + user);




    }

    @Test
    void detail() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}