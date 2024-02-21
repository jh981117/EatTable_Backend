package com.lec.spring.config;


import com.lec.spring.domain.RoleName;
import com.lec.spring.oauth2.CustomSuccessHandler;
import com.lec.spring.service.OAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig   {

    private final TokenProvider tokenProvider;

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    private final CustomUserDetailsService userDetailService;

    private final OAuth2UserService oAuth2UserService;
    private final CustomSuccessHandler customSuccessHandler;


    // PasswordEncoder는 BCryptPasswordEncoder를 사용
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // () 메소드로 체이닝
        // 람다식 ->
        http
                // CSRF(Cross-Site Request Forgery) 공격 방지 비활성화
                .csrf((csrfConfig) ->
                        csrfConfig.disable()
                )

                // HTTP 헤더 설정 중, 프레임 옵션 비활성화
                .headers((headerConfig) ->
                        headerConfig.frameOptions(frameOptionsConfig ->
                                frameOptionsConfig.disable()
                        )
                )


                // HTTP 요청 권한 설정
                .authorizeHttpRequests((authorizeRequests) ->
                        authorizeRequests
                                // 루트 경로와 /login/**에 대한 모든 사용자 접근 허용

                                .requestMatchers("/api/authenticate").permitAll() //
                                .requestMatchers("/api/user/signup").permitAll() // 회원가입 api
                                .requestMatchers("/api/user/login").permitAll() // 로그인 api
                                .requestMatchers("/favicon.ico").permitAll()
                                .requestMatchers("/authenticate/**").hasAuthority(RoleName.ROLE_ADMIN.name())
                                .anyRequest().permitAll()//
                )

                // 예외 처리 관련 설정
                .exceptionHandling((exceptionConfig) ->
                        exceptionConfig.authenticationEntryPoint(jwtAuthenticationEntryPoint).accessDeniedHandler(jwtAccessDeniedHandler)
                )

                .oauth2Login((oauth2 -> oauth2.userInfoEndpoint
                            (((userInfoEndpointConfig) -> userInfoEndpointConfig.userService(oAuth2UserService)))
                        .successHandler(customSuccessHandler)
                            ))

                // 로그인 페이지 및 관련 설정
                .formLogin((formLogin) ->
                        formLogin
                                // 로그인 페이지의 URL
                                .loginPage("/login")
                                // 사용자 이름 및 비밀번호 파라미터 설정
                                .usernameParameter("username")
                                .passwordParameter("password")
                                // 로그인 처리 URL
                                .loginProcessingUrl("/login/login-proc")
                                // 로그인 성공 후 이동할 URL
                                .defaultSuccessUrl("/", true)
                )
                // 로그아웃 관련 설정
                .logout((logoutConfig) ->
                        logoutConfig.logoutSuccessUrl("/")
                )
                .userDetailsService(userDetailService)
                // JwtSecurityConfig 적용
                .apply(new JwtSecurityConfig(tokenProvider));
                // 사용자 상세 정보 서비스 설정


        return http.build();
    }
}