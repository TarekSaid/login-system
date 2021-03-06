package br.com.tarek.login.users.controllers.impl;

import br.com.tarek.login.users.controllers.IUserController;
import br.com.tarek.login.users.entities.impl.User;
import br.com.tarek.login.users.exceptions.UserNotFoundException;
import br.com.tarek.login.users.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Optional;

@RestController
public class UserController implements IUserController {

    @Autowired
    private IUserService userService;

    @Override
    @RequestMapping("/me")
    public User findUser(Principal principal) {
        return Optional
                .ofNullable(userService.findUser(principal.getName()))
                .orElseThrow(() -> new UserNotFoundException("User does not exist."));
    }

    @Override
    @RequestMapping("/user")
    public Principal getLoggedUser(Principal user) {
        return user;
    }
}
