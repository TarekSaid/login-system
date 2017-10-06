package br.com.tarek.login.users.entities;

import br.com.tarek.login.users.entities.impl.Role;
import br.com.tarek.login.users.entities.impl.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

public class AuthPrincipal implements UserDetails {

    private static final long serialVersionUID = -3884508737828470942L;

    private User user;

    public AuthPrincipal(User user) {
        this.user = user;
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }

    @Override
    public boolean isAccountNonExpired() {
        return Boolean.TRUE;
    }

    @Override
    public boolean isAccountNonLocked() {
        return Boolean.TRUE;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return Boolean.TRUE;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Optional.ofNullable(user.getRoles())
                .map(r -> r.parallelStream().map(Role::getRole).map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }
}
