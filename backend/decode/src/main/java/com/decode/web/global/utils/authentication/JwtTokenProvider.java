package com.decode.web.global.utils.authentication;

import com.decode.web.domain.user.service.RedisService;
import com.decode.web.domain.user.service.UserDetailsImpl;
import com.decode.web.domain.user.service.UserDetailsServiceImpl;
import com.decode.web.domain.user.service.UserService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Slf4j
@Component
@Transactional(readOnly = true)
public class JwtTokenProvider {
    private static Key signingKey;
    private final RedisService redisService;

    private final UserDetailsServiceImpl userDetailsService;
    private final String secretKey;
    private final Long accessTokenValidityInMilliseconds;
    private final Long refreshTokenValidityInMilliseconds;


    public JwtTokenProvider(RedisService redisService,
                            UserService userService, UserDetailsServiceImpl userDetailsService,
                            @Value("${jwt.secret}") String secretKey,
                            @Value("${jwt.access-token-expire-length}") Long accessTokenValidityInMilliseconds,
                            @Value("${jwt.refresh-token-expire-length}") Long refreshTokenValidityInMilliseconds) {
        this.redisService = redisService;
        this.userDetailsService = userDetailsService;

        this.secretKey = secretKey;
        this.accessTokenValidityInMilliseconds = accessTokenValidityInMilliseconds;
        this.refreshTokenValidityInMilliseconds = refreshTokenValidityInMilliseconds;
    }

    @PostConstruct
    public void init() {
        byte[] secretKeyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        signingKey = Keys.hmacShaKeyFor(secretKeyBytes);
        log.info("signingKey: {}", signingKey);
        log.info("secretKey: {}", secretKey);


    }

    public String createToken(String email, String subject, Long validityInMilliseconds) {
        Long now = System.currentTimeMillis();
        Long userId = userDetailsService.loadUserByUsername(email).getId();

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS512")
                .setExpiration(new Date(now + validityInMilliseconds))
                .setSubject(subject)
                .claim("email", email)
                .claim("userId", userId)
                .signWith(signingKey, SignatureAlgorithm.HS512)
                .compact();
    }

    public String createAccessToken(String email) {
        return createToken(email, "access", accessTokenValidityInMilliseconds);
    }

    public String createRefreshToken(String email) {
        return createToken(email, "refresh", refreshTokenValidityInMilliseconds);
    }

    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Authentication getAuthentication(String token) {
        String email = getClaims(token).get("email", String.class);
        UserDetailsImpl userDetailsImpl = userDetailsService.loadUserByUsername(email);
        return new UsernamePasswordAuthenticationToken(userDetailsImpl, "");
    }
    public Long getAuthUserId(String token) {
        return getClaims(token).get("userId", Long.class);
    }

    public long getTokenExpirationTime(String token) {
        return getClaims(token).getExpiration().getTime();
    }

    public boolean validateToken(String token) {
        try {
            if (redisService.getValues(token) != null && redisService.getValues(token).equals("logout")) {
                return false;
            }
            Jwts.parserBuilder()
                    .setSigningKey(signingKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            log.error("Invalid JWT signature.");
            throw new SignatureException("Invalid JWT signature.");
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token.");
            throw new MalformedJwtException("Invalid JWT token.");
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token.");
            throw new ExpiredJwtException(null, null, "Expired JWT token.");
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token.");
            throw new UnsupportedJwtException("Unsupported JWT token.");
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty.");
            throw new IllegalArgumentException("JWT claims string is empty.");
        } catch (NullPointerException e) {
            log.error("JWT Token is empty.");
            throw new NullPointerException("JWT Token is empty.");
        }
    }

    public boolean validateTokenExpired(String token) {
        try {
            return getClaims(token)
                    .getExpiration()
                    .before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }


}
