package com.decode.web.global.utils.authentication;

import com.decode.web.domain.user.service.RedisService;
import com.decode.web.domain.user.service.UserService;
import com.decode.web.entity.UserInfoEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
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
    private final UserService userService;
    private final String secretKey;
    private final Long accessTokenValidityInMilliseconds;
    private final Long refreshTokenValidityInMilliseconds;


    public JwtTokenProvider(RedisService redisService,
                            UserService userService,
                            @Value("${jwt.secret}") String secretKey,
                            @Value("${jwt.access-token-expire-length}") Long accessTokenValidityInMilliseconds,
                            @Value("${jwt.refresh-token-expire-length}") Long refreshTokenValidityInMilliseconds) {
        this.redisService = redisService;
        this.userService = userService;
        this.secretKey = secretKey;
        this.accessTokenValidityInMilliseconds = accessTokenValidityInMilliseconds;
        this.refreshTokenValidityInMilliseconds = refreshTokenValidityInMilliseconds;
    }

    @PostConstruct
    public void init() {
        byte[] secretKeyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        signingKey = Keys.hmacShaKeyFor(secretKeyBytes);

    }

    public String createToken(String email, String subject, Long validityInMilliseconds) {
        Long now = System.currentTimeMillis();

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS512")
                .setExpiration(new Date(now + validityInMilliseconds))
                .setSubject(subject)
                .claim("email", email)
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
        UserInfoEntity userInfoEntity = userService.getUserByEmail(email);
        return new UsernamePasswordAuthenticationToken(userInfoEntity, "");
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
        } catch (ExpiredJwtException e) {
            return true;

        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
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
