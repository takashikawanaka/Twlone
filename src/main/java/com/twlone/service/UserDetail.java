package com.twlone.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.twlone.entity.Authorization;
import com.twlone.entity.User;

public class UserDetail implements UserDetails {
    private static final long serialVersionUID = 1L;

    private final Authorization authorization;
    private final List<SimpleGrantedAuthority> authorities;

    public UserDetail(Authorization authorization) {
        this.authorization = authorization;
        List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("user"));
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public User getUser() {
        return authorization.getUser();
    }

    @Override
    public String getPassword() {
        return authorization.getPassword();
    }

    @Override
    public String getUsername() {
        return getUser().getUserId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
