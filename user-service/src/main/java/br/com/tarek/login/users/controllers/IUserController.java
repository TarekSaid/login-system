package br.com.tarek.login.users.controllers;

import br.com.tarek.login.users.entities.impl.User;

import java.security.Principal;

public interface IUserController {

    User findUser(Principal principal);
}
