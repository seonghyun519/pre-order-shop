package com.sparklep.preorder.global.security;

import com.sparklep.preorder.domain.user.entity.User;
import com.sparklep.preorder.domain.user.entity.UserRoleEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Slf4j
@Getter
@RequiredArgsConstructor
public class UserDetailsImpl implements UserDetails {

    private final User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        log.info("getAuthorities");
        UserRoleEnum role = user.getRole();
        String authority = role.getAuthority();

        // 권한 목록 생성
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authority);
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(simpleGrantedAuthority);

        return authorities;
    }

    @Override
    public String getPassword() {
        log.info("getPassword");
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        log.info("getUsername");
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        log.info("isAccountNonExpired");
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        log.info("isAccountNonLocked");
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        log.info("isCredentialsNonExpired");
        return true;
    }

    @Override
    public boolean isEnabled() {
        log.info("isEnabled");
        return true;
    }
}