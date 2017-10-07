package br.com.tarek.login.profiles.providers;

import br.com.tarek.login.profiles.entities.impl.Profile;
import br.com.tarek.login.profiles.entities.impl.User;
import org.testng.annotations.DataProvider;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ProfileDataProvider {

    @DataProvider(name = "users")
    public Iterator<Object[]> createUsers() {
        List<Object[]> users = new ArrayList<>();

        users.add(new Object[]{new User()});
        users.add(new Object[]{new User(1L)});
        users.add(new Object[]{new User(Long.MAX_VALUE)});
        users.add(new Object[]{new User(Long.MIN_VALUE)});

        return users.iterator();
    }

    @DataProvider(name = "profiles")
    public Iterator<Object[]> getProfiles() {
        List<Object[]> profiles = new ArrayList<>();

        profiles.add(new Object[]{new Profile()});
        profiles.add(new Object[]{new Profile(null, null, null)});
        profiles.add(new Object[]{new Profile("Test", null, null)});
        profiles.add(new Object[]{new Profile("", "", "")});
        profiles.add(new Object[]{new Profile("Name", "Business", "Website")});

        return profiles.iterator();
    }
}
