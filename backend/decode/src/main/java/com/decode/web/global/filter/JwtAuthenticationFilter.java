package com.decode.web.global.filter;

import com.decode.web.domain.user.service.AuthService;
import com.decode.web.global.utils.authentication.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest req,
            HttpServletResponse res,
            FilterChain filterChain) throws ServletException, IOException {
        String accessToken = resolveToken(req);
        String refreshToken = null;
        Cookie[] list = req.getCookies();
        for (Cookie cookie : list) {
            if(cookie.getName().equals("refresh-token")) refreshToken = cookie.getValue();
        }
        if(accessToken != null){
            if (jwtTokenProvider.validateToken(accessToken)) {
                // token 유효성 검사 후 securityContext에 저장
                Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            else if( refreshToken != null && jwtTokenProvider.validateToken(refreshToken) && authService.validateRefreshTokenInRedis(refreshToken)){
                // 토큰 재발급
                String principal = jwtTokenProvider.getPrincipal(refreshToken);
                String provider = jwtTokenProvider.getProvider(refreshToken);
                String newAccessToken = jwtTokenProvider.createAccessToken(principal, provider);

                // securityContext에 저장
                Authentication authentication = jwtTokenProvider.getAuthentication(newAccessToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);

                // 쿠키에 새로운 토큰 저장
                res.setHeader("Authorization", "Bearer " + newAccessToken);
            }
            else{
                // 로그아웃 처리
                SecurityContextHolder.clearContext();
            }
        }

        filterChain.doFilter(req, res);

    }

    private String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        log.info("bearerToken : {}", bearerToken);
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;

    }
}
