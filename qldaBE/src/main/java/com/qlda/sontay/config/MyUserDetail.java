package com.qlda.sontay.config;

import com.qlda.sontay.common.UserStatus;
import com.qlda.sontay.domain.Role;
import com.qlda.sontay.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

public record MyUserDetail(User user) implements UserDetails {

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Role role = user.getRole();
        if (role == null) {
            return Set.of();
        }
        return Set.of(new SimpleGrantedAuthority(role.getCode()));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // hoặc map theo field trong User
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // hoặc map theo field trong User
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // hoặc map theo field trong User
    }

    @Override
    public boolean isEnabled() {
        return UserStatus.ACTIVE.equals(user.getStatus());
    }
}
