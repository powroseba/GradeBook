package com.application.rest;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/")
public class TempAuthTestController {

    @GetMapping(value = "admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String getAdmin(){
        return "admin";
    }

    @GetMapping(value = "teacher")
    @PreAuthorize("hasRole('TEACHER')")
    public String getTeacher(){
        return "teacher";
    }

    @GetMapping(value = "student")
    @PreAuthorize("hasRole('STUDENT')")
    public String getStudent(){
        return "student";
    }

    @GetMapping(value = "username")
    public String username(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        Claims claims = Jwts.parser()
                .setSigningKey("ThisIsASecret")
                .parseClaimsJws(token.replace("Bearer", "")).getBody();
        return claims.getSubject();
    }
}
