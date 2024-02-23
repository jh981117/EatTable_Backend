package com.lec.spring.config.WebSocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSocketSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/ws/**").permitAll() // WebSocket 경로는 인증하지 않음
                                .anyRequest().authenticated() // 그 외의 모든 요청은 인증 필요
                )
                .csrf().disable(); // CSRF 보안 비활성화 (WebSocket은 CSRF 공격에 노출되지 않음)
        return http.build();
    }
}