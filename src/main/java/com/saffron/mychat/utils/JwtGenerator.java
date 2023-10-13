package com.saffron.mychat.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.util.Date;

public class JwtGenerator {

    public static String generateToken(String email) {
        return Jwts.builder()
                .subject(email)
                .expiration(new Date(System.currentTimeMillis() + 864_000_000)) // 10 days
                .signWith(Keys.hmacShaKeyFor("YourSecretKey".getBytes()))
                .compact();
    }
}

