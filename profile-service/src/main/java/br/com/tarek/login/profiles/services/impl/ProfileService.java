package br.com.tarek.login.profiles.services.impl;

import br.com.tarek.login.profiles.entities.impl.Profile;
import br.com.tarek.login.profiles.exceptions.impl.ProfileNotFoundException;
import br.com.tarek.login.profiles.exceptions.impl.UserNotFoundException;
import br.com.tarek.login.profiles.repositories.ProfileRepository;
import br.com.tarek.login.profiles.resources.UserResource;
import br.com.tarek.login.profiles.services.IProfilerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileService implements IProfilerService {

    @Autowired
    private UserResource userResource;

    @Autowired
    private ProfileRepository profileRepository;

    @Override
    public Profile getProfile() {
        return Optional.ofNullable(userResource.getLoggedUser())
                .map(u -> Optional.ofNullable(profileRepository.findByUserId(u.getId()))
                        .orElseThrow(() -> new ProfileNotFoundException("Profile not found")))
                .orElseThrow(() -> new UserNotFoundException("User not found."));
    }
}
