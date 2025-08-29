package com.example._250828_spring_practice_customuserdetails.security;

import com.example._250828_spring_practice_customuserdetails.model.Role;
import com.example._250828_spring_practice_customuserdetails.model.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {
    @Getter
    private final User user;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Role> roles = user.getRoles();
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getAuthority())).collect(Collectors.toSet());
    }
    @Override
    public String getPassword() {
        return user.getPassword();
    }
    @Override
    public String getUsername() {
        return user.getUsername();
    }
    public boolean isAccountNonExpired() {
        return user.getAccountNonExpired();
    }
    public boolean isCredentialsNonExpired() {
        return user.getCredentialsNonExpired();
    }
    public boolean isEnabled(){
        return user.getEnabled();
    }
    public String getEmail() {
        return user.getEmail();
    }
    public String getFullName() {
        return user.getFullName();
    }
    public Long getUserId() {
        return user.getId();
    }
}
