package com.example._250828_spring_practice_customuserdetails.security;

import com.example._250828_spring_practice_customuserdetails.model.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public class CustomerUserDetails implements UserDetails {
    @Getter
    private final User user;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }
    @Override
    public String getPassword() {
        return "";
    }
    @Override
    public String getUsername() {
        return "";
    }
    public boolean isAccountedNonExpired() {
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
