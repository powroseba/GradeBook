package com.application.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.GrantedAuthority;

import java.time.Instant;
import java.util.Collection;
import java.util.Date;

public class TokenBuilder {
    private static final String SECRET = "ThisIsASecret";

    public static String createToken(String username, int time, Collection<? extends GrantedAuthority> authorities) {

        Date EXPIRATION = Date.from(Instant.now().plusSeconds(time));
        Claims claims = Jwts.claims().setSubject(username);

        claims.put("role", authorities);

        String jwt = Jwts.builder()
                .setClaims(claims)
                .setExpiration(EXPIRATION)
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();

        return jwt;
    }
}