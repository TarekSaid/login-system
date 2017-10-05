package br.com.tarek.login.users.controllers.impl;

import br.com.tarek.login.users.entities.impl.User;
import br.com.tarek.login.users.providers.UserDataProvider;
import br.com.tarek.login.users.services.IUserService;
import mockit.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.testng.annotations.Test;

import java.security.Principal;

import static org.assertj.core.api.Assertions.*;

@Test
public class UserControllerTest {

    @Tested
    private UserController userController;

    @Injectable
    private IUserService userService;

    @Mocked
    private Principal principal;

    public void findUserShouldCallUserService() {
        userController.findUser(principal);

        new Verifications() {{
            userService.findUser(principal.getName());
        }};
    }

    @Test(dataProviderClass = UserDataProvider.class, dataProvider = "users")
    public void findUserShouldReturnUserFound(User user) {
        new Expectations() {{
            userService.findUser(principal.getName());
            result = user;
        }};

        assertThat(userController.findUser(principal)).isEqualTo(user);
    }

    @Test(expectedExceptions = UsernameNotFoundException.class)
    public void findMeShouldThrowErrorIfUserNotFound() {
        new Expectations() {{
            userService.findUser(principal.getName());
            result = null;
        }};

        userController.findUser(principal);
    }

    @Test
    public void getLoggedUserShouldReturnUser(@Mocked Principal user) {
        assertThat(userController.getLoggedUser(user)).isEqualTo(user);
    }
}
