package com.decode.web.global.config;

import java.util.List;
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

        // 자격 증명 허용
        config.setAllowCredentials(true);

        // 개발 중에는 모든 오리진 허용
        config.setAllowedOriginPatterns(List.of("*"));
//        config.addAllowedOrigin("*");
        config.addExposedHeader("Authorization");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*"); // 모든 메서드 허용

        source.registerCorsConfiguration("/**", config); // 모든 경로에서 CORS 허용

        return new CorsFilter(source);
    }
}