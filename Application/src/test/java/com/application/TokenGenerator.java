package com.application;

import com.application.security.TokenBuilder;
import com.entities.UserRole;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
public class TokenGenerator {

    public String generate(int time, String username, UserRole userRole) {
        Set<GrantedAuthority> roles = new HashSet<GrantedAuthority>(){
            {
                add(new SimpleGrantedAuthority(UserRole.getAutority(userRole.name())));
            }
        };
        return TokenBuilder.createToken(username, time, roles);
    }
}
