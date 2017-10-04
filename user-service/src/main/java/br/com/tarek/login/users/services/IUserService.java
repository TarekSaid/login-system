package br.com.tarek.login.users.services;

import br.com.tarek.login.users.entities.impl.User;

public interface IUserService {

    User findUser(String username);
}
