package com.lec.spring.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 모든 경로에 대하여
                .allowedOriginPatterns("*") // 모든 출처 허용 패턴 사용
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
                .allowedHeaders("*")
                .exposedHeaders("Refresh-Token") // 클라이언트에서 접근 가능하도록 Refresh-Token 헤더 노출
                .allowCredentials(true)
                .maxAge(3600); // 1시간 동안 pre-flight 결과 캐시
    }
}
