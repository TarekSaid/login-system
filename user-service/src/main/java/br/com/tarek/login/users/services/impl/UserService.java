package br.com.tarek.login.users.services.impl;

import br.com.tarek.login.users.entities.impl.User;
import br.com.tarek.login.users.repositories.UserRepository;
import br.com.tarek.login.users.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findUser(String username) {
        try {
            return userRepository.findByUsername(username);
        } catch (EmptyResultDataAccessException dae) {
            return null;
        }
    }
}
