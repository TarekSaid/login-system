package br.com.tarek.login.profiles.clients.impl;

import br.com.tarek.login.profiles.clients.UserClient;
import br.com.tarek.login.profiles.entities.impl.User;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class TestUserClient implements UserClient {

    @Override
    public User getLoggedUser() {
        User user = new User();

        user.setId(1L);
        user.setUsername("user");
        user.setPassword("password");

        return user;
    }
}
