package br.com.tarek.login.profiles.resources.impl;

import br.com.tarek.login.profiles.entities.impl.User;
import br.com.tarek.login.profiles.resources.UserResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class TestUserResource implements UserResource {

    @Override
    public User getLoggedUser() {
        User user = new User();

        user.setId(1L);
        user.setUsername("user");
        user.setPassword("password");

        return user;
    }
}
