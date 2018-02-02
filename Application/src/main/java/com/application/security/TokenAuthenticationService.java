package com.application.security;

import com.entities.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

public class TokenAuthenticationService {
    private static final String SECRET = "ThisIsASecret";
    private static final String TOKEN_PREFIX = "Bearer";
    private static final String HEADER_STRING = "Authorization";

    private static final int TOKEN_TIME = 172800;
    private static final String TOKEN_HEADER_TEMPLATE = "Bearer %s";

    static void addAuthentication(HttpServletResponse res, String username, Collection<? extends GrantedAuthority> authorities) {
        String JWT = TokenBuilder.createToken(username, TOKEN_TIME, authorities);
        res.addHeader(HEADER_STRING, String.format(TOKEN_HEADER_TEMPLATE, JWT));
    }

    static Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        if (token != null) {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, "")).getBody();


            if (claims.getExpiration().before(new Date())) {
                return null;
            }


            return claims.getSubject() != null ?
                    new UsernamePasswordAuthenticationToken(claims.getSubject(),
                            null,
                            generateAuthorities(claims.get("role").toString())) :
                    null;
        }
        return null;
    }

    static Collection <GrantedAuthority> generateAuthorities(String roleChain) {
        Collection <GrantedAuthority> authorities = new HashSet<>();

        if (roleChain.contains(UserRole.ADMIN.name())) {
            authorities.add(new SimpleGrantedAuthority(UserRole.getAutority("ADMIN")));
        }
        if (roleChain.contains(UserRole.TEACHER.name())) {
            authorities.add(new SimpleGrantedAuthority(UserRole.getAutority("TEACHER")));
        }
        if (roleChain.contains(UserRole.STUDENT.name())) {
            authorities.add(new SimpleGrantedAuthority(UserRole.getAutority("STUDENT")));
        }

        return authorities;
    }
}