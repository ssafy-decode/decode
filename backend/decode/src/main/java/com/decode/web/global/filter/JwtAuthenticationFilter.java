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
        if (list != null) {
            for (Cookie cookie : list) {
                if (cookie.getName().equals("refresh-token")) {
                    refreshToken = cookie.getValue();
                }
            }
        }
        log.info("accessToken : {}", accessToken);
        if (accessToken != null && jwtTokenProvider.validateToken(accessToken)) {
            // token 유효성 검사 후 securityContext에 저장
            Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.info("인증 정보 저장");

            res.setHeader("Authorization", "Bearer " + accessToken);
        } else if (refreshToken != null
                && jwtTokenProvider.validateToken(refreshToken)
                && authService.validateRefreshTokenInRedis(refreshToken)) {
            // refreshToken 유효성 검사 후 accessToken 재발급
            accessToken = reIssueAccessToken(refreshToken);
            res.setHeader("Authorization", "Bearer " + accessToken);
        } else {
            SecurityContextHolder.clearContext();
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

    private String reIssueAccessToken(String refreshToken) {
        // 토큰 재발급
        log.info("토큰 재발급");
        String principal = jwtTokenProvider.getPrincipal(refreshToken);
        String provider = jwtTokenProvider.getProvider(refreshToken);
        String newAccessToken = jwtTokenProvider.createAccessToken(principal, provider);

        // securityContext에 저장
        Authentication authentication = jwtTokenProvider.getAuthentication(newAccessToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return newAccessToken;


    }
}
