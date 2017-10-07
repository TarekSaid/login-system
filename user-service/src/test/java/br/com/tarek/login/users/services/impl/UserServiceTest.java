package br.com.tarek.login.users.services.impl;

import br.com.tarek.login.users.entities.impl.User;
import br.com.tarek.login.users.providers.UserDataProvider;
import br.com.tarek.login.users.repositories.UserRepository;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.Verifications;
import org.springframework.dao.EmptyResultDataAccessException;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.*;

public class UserServiceTest {

    @Tested
    private UserService userService;

    @Injectable
    private UserRepository userRepository;

    @Test(dataProviderClass = UserDataProvider.class, dataProvider = "usernames")
    public void findUserShouldCallRepository(String username) {
        userService.findUser(username);

        new Verifications() {{
            userRepository.findByUsername(username);
        }};
    }

    @Test(dataProviderClass = UserDataProvider.class, dataProvider = "users")
    public void findUserShouldReturnUser(User user) {
        new Expectations() {{
            userRepository.findByUsername(anyString);
            result = user;
        }};

        assertThat(userService.findUser("")).isEqualTo(user);
    }

    @Test
    public void findUserShouldReturnNullWhenNotFound() {
        new Expectations() {{
            userRepository.findByUsername(anyString);
            result = new EmptyResultDataAccessException(1);
        }};

        assertThat(userService.findUser("")).isNull();
    }
}
