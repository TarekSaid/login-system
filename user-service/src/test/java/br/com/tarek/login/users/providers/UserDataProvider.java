package br.com.tarek.login.users.providers;

import br.com.tarek.login.users.entities.impl.User;
import org.testng.annotations.DataProvider;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UserDataProvider {

    @DataProvider(name = "users")
    public Iterator<Object[]> createUsers() {
        List<Object[]> users = new ArrayList<>();

        users.add(new Object[]{new User()});
        users.add(new Object[]{new User("")});
        users.add(new Object[]{new User("teste1")});

        return users.iterator();
    }

    @DataProvider(name = "usernames")
    public Iterator<Object[]> createUsernames() {
        List<Object[]> usernames = new ArrayList<>();

        usernames.add(new Object[]{null});
        usernames.add(new Object[]{""});
        usernames.add(new Object[]{"teste1"});

        return usernames.iterator();
    }
}
