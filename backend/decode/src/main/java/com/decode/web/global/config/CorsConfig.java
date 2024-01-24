package com.decode.web.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
@Configuration
public class CorsConfig {

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // 특정 경로에 대한 CORS 설정
        config.setAllowCredentials(true);

        // 실제 허용할 도메인
        config.addAllowedOrigin("http://i10a507.p.ssafy.io");
        config.addAllowedOrigin("https://i10a507.p.ssafy.io");
        config.addAllowedOrigin("http://i10a507.p.ssafy.io:7777");
        config.addAllowedOrigin("https://i10a507.p.ssafy.io:7777");

        config.addAllowedHeader("*");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("OPTIONS"); // OPTIONS 메서드는 CORS 사전 검사 요청을 위해 필요할 수 있음
        // 필요한 경우 다른 CORS 설정도 추가 가능

        source.registerCorsConfiguration("/decode", config); // 특정 경로에만 설정 적용

        return new CorsFilter(source);
    }
}