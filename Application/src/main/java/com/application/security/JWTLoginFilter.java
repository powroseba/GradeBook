package com.application.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.repositories.UserModelRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

@Component
public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {



    private UserModelRepository userModelRepository;

    public JWTLoginFilter(AuthenticationManager authManager, UserModelRepository userModelRepository) {
        super(new AntPathRequestMatcher("/login"));
        setAuthenticationManager(authManager);
        this.userModelRepository = userModelRepository;
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest req, HttpServletResponse res)
            throws AuthenticationException, IOException, ServletException {
        AccountCredentials creds = new ObjectMapper()
                .readValue(req.getInputStream(), AccountCredentials.class);


        if (!Optional.ofNullable(userModelRepository.findByUsername(creds.getUsername())).isPresent()) {
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }

        Collection <? extends GrantedAuthority> autorities = getUserAuthorities(creds.getUsername());

        return getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(
                        creds.getUsername(),
                        creds.getPassword(),
                        autorities
                )
        );
    }


    private Collection<? extends GrantedAuthority> getUserAuthorities(String username){
        return userModelRepository.findByUsername(username).getAuthorities();
    }

    @Override
    protected void successfulAuthentication( HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth)
            throws IOException, ServletException {
        TokenAuthenticationService
                .addAuthentication(res, auth.getName(), auth.getAuthorities());
    }

}