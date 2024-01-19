package com.decode.web.global.utils.authentication;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtUtil{
    public static String createToken(String user_email, String secretKey, Long expireTime){
        Claims claims = Jwts.claims();
        claims.put("user_email", user_email);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+expireTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
}
