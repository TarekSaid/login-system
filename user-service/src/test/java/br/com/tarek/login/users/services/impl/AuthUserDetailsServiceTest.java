package br.com.tarek.login.users.services.impl;

import br.com.tarek.login.users.entities.impl.Role;
import br.com.tarek.login.users.entities.impl.User;
import br.com.tarek.login.users.exceptions.UserNotFoundException;
import br.com.tarek.login.users.providers.UserDataProvider;
import br.com.tarek.login.users.repositories.UserRepository;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.Verifications;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class AuthUserDetailsServiceTest {

    @Tested
    private AuthUserDetailsService userDetailsService;

    @Injectable
    private UserRepository userRepository;

    @Test(dataProviderClass = UserDataProvider.class, dataProvider = "usernames")
    public void loadUserByUsernameShouldCallUserRepository(String username) {
        userDetailsService.loadUserByUsername(username);

        new Verifications() {{
            userRepository.findByUsername(username);
        }};
    }

    @Test(expectedExceptions = UserNotFoundException.class)
    public void loadByUsernameShouldThrowErrorIfUserNotFound() {
        new Expectations() {{
            userRepository.findByUsername(anyString);
            result = null;
        }};

        userDetailsService.loadUserByUsername("");
    }

    @Test(dataProviderClass = UserDataProvider.class, dataProvider = "userRoles")
    public void loadByUsernameShouldReturnAuthPrincipal(User user) {
        new Expectations() {{
            userRepository.findByUsername(anyString);
            result = user;
        }};

        UserDetails userDetails = userDetailsService.loadUserByUsername("");

        assertThat(userDetails.getUsername()).isEqualTo(user.getUsername());
        assertThat(userDetails.getPassword()).isEqualTo(user.getPassword());

        // criando lista de Authorities a partir da Lista de Roles
        List<? extends GrantedAuthority> authorities = Optional.ofNullable(user.getRoles())
                .map(r -> r.parallelStream().map(Role::getRole).map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());

        assertThat(userDetails.getAuthorities()).isEqualTo(authorities);
    }
}
