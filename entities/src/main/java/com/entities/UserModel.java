package com.entities;

import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Table(name = "USER_MODEL")
@Entity
@NoArgsConstructor
public class UserModel implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email
    private String email;

    @NotNull
    @Size(min = 4, max = 15)
    private String username;

    @NotNull
    @Size(min = 8)
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> roles = new HashSet<>();

    @OneToOne(fetch = FetchType.EAGER)
    private Student student;

    @OneToOne(fetch = FetchType.EAGER)
    private Teacher teacher;

    public UserModel(String email, String username, String password, String role) {
        this.email = email;
        this.username = username;
        setPassword(password);
        setRoles(role);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> autorities = new HashSet<>();
        for (String role : roles) {
            autorities.add(new SimpleGrantedAuthority(UserRole.getAutority(role)));
        }
        return autorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    public void setRoles(String role) {
        if (role.equals(UserRole.ADMIN.name()) ||
                role.equals(UserRole.STUDENT.name()) ||
                role.equals(UserRole.TEACHER.name())) {
            this.roles.add(UserRole.valueOf(role).name());
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = new BCryptPasswordEncoder().encode(password);
    }

    public void setUserDetails(UserModelDetails userModelDetails) {
        if (this.roles.contains(UserRole.TEACHER.name())) {
            this.teacher = (Teacher) userModelDetails;
        }
        if (this.roles.contains(UserRole.STUDENT.name())) {
            this.student = (Student)userModelDetails;
        }
    }

    public UserModelDetails getUserModelDetails() {
        if (this.roles.contains(UserRole.TEACHER.name())) {
            return teacher;
        }
        if (this.roles.contains(UserRole.STUDENT.name())) {
            return student;
        }
        return null;
    }
}

