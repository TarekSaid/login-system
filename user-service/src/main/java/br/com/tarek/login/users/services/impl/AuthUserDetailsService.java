package br.com.tarek.login.users.services.impl;

import br.com.tarek.login.users.entities.AuthPrincipal;
import br.com.tarek.login.users.exceptions.UserNotFoundException;
import br.com.tarek.login.users.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return Optional.ofNullable(userRepository.findByUsername(username))
                .map(AuthPrincipal::new)
                .orElseThrow(() -> new UserNotFoundException("User does not exist."));
    }
}
