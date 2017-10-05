package br.com.tarek.login.profiles.controllers.impl;

import br.com.tarek.login.profiles.controllers.IProfileController;
import br.com.tarek.login.profiles.entities.impl.Profile;
import br.com.tarek.login.profiles.exceptions.impl.ProfileNotFoundException;
import br.com.tarek.login.profiles.services.IProfilerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class ProfileController implements IProfileController {

    @Autowired
    private IProfilerService profile;

    @Override
    @RequestMapping("/")
    public Profile getProfile() throws ProfileNotFoundException {
        return Optional.ofNullable(profile.getProfile())
                .orElseThrow(() -> new ProfileNotFoundException("No profile found for user."));
    }
}
