package com.application.tools;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import javax.servlet.http.HttpServletRequest;

public class TokenUsernameParserService {

    private static final String SECRET = "ThisIsASecret";
    private static final String TOKEN_PREFIX = "Bearer";
    private static final String HEADER_STRING = "Authorization";

    public static String parseUsername(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        Claims claims = Jwts.parser()
                .setSigningKey("ThisIsASecret")
                .parseClaimsJws(token.replace("Bearer", "")).getBody();
        return claims.getSubject();
    }
}
