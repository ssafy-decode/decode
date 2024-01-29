package com.decode.web.global.utils.authentication;

import com.decode.web.domain.user.service.RedisService;
import com.decode.web.domain.user.service.UserDetailsImpl;
import com.decode.web.domain.user.service.UserDetailsServiceImpl;
import com.decode.web.domain.user.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@Transactional(readOnly = true)
public class JwtTokenProvider {

    private final String PROVIDER = "Server";
    private static Key signingKey;

    private final UserDetailsServiceImpl userDetailsService;
    private final String secretKey;
    private final RedisService redisService;
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

    public String createToken(String email, String subject, Long validityInMilliseconds,
            String provider) {
        Long now = System.currentTimeMillis();
        Long userId = userDetailsService.loadUserByUsername(email).getId();

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS512")
                .setExpiration(new Date(now + validityInMilliseconds))
                .setSubject(subject)
                .claim("email", email)
                .claim("userId", userId)
                .claim("provider", provider)
                .signWith(signingKey, SignatureAlgorithm.HS512)
                .compact();
    }

    public String createAccessToken(String email, String provider) {
        return createToken(email, "access", accessTokenValidityInMilliseconds, provider);
    }

    public String createRefreshToken(String email, String provider) {
        return createToken(email, "refresh", refreshTokenValidityInMilliseconds, provider);
    }

    public Claims getClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(signingKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

        } catch (ExpiredJwtException e) {
            log.info(e.getMessage());
            throw new ExpiredJwtException(null, null, e.getMessage());
        }
    }

    public Authentication getAuthentication(String token) {
        String email = getClaims(token).get("email", String.class);
        UserDetailsImpl userDetailsImpl = userDetailsService.loadUserByUsername(email);
        log.info(userDetailsImpl.toString());
        return new UsernamePasswordAuthenticationToken(userDetailsImpl.getId(),
                userDetailsImpl.getPassword(), userDetailsImpl.getAuthorities());
    }

    public Long getAuthUserId(String token) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        return getClaims(token).get("userId", Long.class);
    }

    public String getProvider(String token) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        return getClaims(token).get("provider", String.class);
    }

    public long getTokenExpirationTime(String token) {
        return getClaims(token).getExpiration().getTime();
    }

    public boolean validateToken(String token) {
        try {
            return !getClaims(token).getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            log.error("access token expired");
            return false;
        }
    }

    public String getPrincipal(String token) {
        return getClaims(token).get("email", String.class);
    }
//    public boolean validateToken(String token) {
//        try {
//            Jwts.parserBuilder()
//                    .setSigningKey(signingKey)
//                    .build()
//                    .parseClaimsJws(token);
//            return true;
//        } catch (ExpiredJwtException e) {
//            log.error("access token expired");
//        } catch (SignatureException e) {
//            log.error("Invalid JWT signature.");
//        } catch (MalformedJwtException e) {
//            log.error("Invalid JWT token.");
//        } catch (UnsupportedJwtException e) {
//            log.error("Unsupported JWT token.");
//        } catch (IllegalArgumentException e) {
//            log.error("JWT claims string is empty.");
//        } catch (NullPointerException e) {
//            log.error("JWT Token is empty.");
//
//        }
//        return false;
//    }


}
