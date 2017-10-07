package br.com.tarek.login.profiles.services.impl;

import br.com.tarek.login.profiles.entities.impl.Profile;
import br.com.tarek.login.profiles.entities.impl.User;
import br.com.tarek.login.profiles.repositories.ProfileRepository;
import br.com.tarek.login.profiles.services.IProfilerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileService implements IProfilerService {

    @Autowired
    @LoadBalanced
    private OAuth2RestTemplate oAuth2RestTemplate;

    @Autowired
    private ProfileRepository profileRepository;

    @Override
    public Profile getProfile() {
        try {
            return Optional.ofNullable(oAuth2RestTemplate.getForObject("http://user-service/me", User.class))
                    .map(u -> profileRepository.findByUserId(u.getId()))
                    .orElse(null);
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }
}
