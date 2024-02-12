package com.decode.web.global.config;


import com.decode.web.domain.user.service.AuthService;
import com.decode.web.global.filter.JwtAuthenticationFilter;
import com.decode.web.global.utils.authentication.JwtAccessDeniedHandler;
import com.decode.web.global.utils.authentication.JwtAuthenticationEntryPoint;
import com.decode.web.global.utils.authentication.JwtTokenProvider;
import com.decode.web.global.utils.oauth2.OAuth2AuthenticationFailureHandler;
import com.decode.web.global.utils.oauth2.OAuth2AuthenticationSuccessHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsUtils;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j

public class SecurityConfig {

    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtTokenProvider jwtTokenProvider;
    private final CorsConfig corsconfig;
    private final AuthService authservice;


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                        .requestMatchers(HttpMethod.GET).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/login"),
                                new AntPathRequestMatcher("/regist")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/email")
                                , new AntPathRequestMatcher("/password")
                                , new AntPathRequestMatcher("/addUserTag")
                                , new AntPathRequestMatcher("/image")
                                , new AntPathRequestMatcher("/oauth2/authorization/**")
                                , new AntPathRequestMatcher("/oauth2/callback/**")

                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .httpBasic(httpBasic ->
                        httpBasic.disable()
                )
                .csrf(csrf ->
                        csrf.disable()
                )

                .logout(logout ->
                        logout.disable()
                )
                .formLogin(formLogin ->
                        formLogin.disable()
                )
                .oauth2Login(oauth2Login -> oauth2Login
                        .authorizationEndpoint(authorizationEndpoint -> authorizationEndpoint
                                .baseUri("/oauth2/authorization")
                        )
                        .redirectionEndpoint(redirectionEndpoint -> redirectionEndpoint
                                .baseUri("/oauth2/callback/*")
                        )

                        .successHandler(oAuth2AuthenticationSuccessHandler)
                        .failureHandler(oAuth2AuthenticationFailureHandler)

                )
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                        .accessDeniedHandler(jwtAccessDeniedHandler)
                )
                .addFilter(corsconfig.corsFilter())
                .addFilterBefore(new JwtAuthenticationFilter(authservice, jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }
}
