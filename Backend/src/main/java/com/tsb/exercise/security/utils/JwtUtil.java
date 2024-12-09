package com.tsb.exercise.security.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;


import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    private static final String SECRET_KEY = "1234567890abcdefghijklmnopqrstuv";
    private final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    public  String generateToken(String userId){
        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 1200)) // 120 minutes
                .signWith(key)
                .compact();
    }
    public String validateTokenAndGetUserId(String token) {
        Key key = new SecretKeySpec(SECRET_KEY.getBytes(), SignatureAlgorithm.HS256.getJcaName());
        try {
            Claims body = Jwts.parser()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return body.getSubject();
        } catch (JwtException | IllegalArgumentException e) {
            return null;
        }
    }
}
