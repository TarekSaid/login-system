package br.com.tarek.login.profiles.services.impl;

import br.com.tarek.login.profiles.entities.impl.Profile;
import br.com.tarek.login.profiles.entities.impl.User;
import br.com.tarek.login.profiles.exceptions.impl.ProfileNotFoundException;
import br.com.tarek.login.profiles.exceptions.impl.UserNotFoundException;
import br.com.tarek.login.profiles.providers.ProfileDataProvider;
import br.com.tarek.login.profiles.repositories.ProfileRepository;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.Verifications;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.testng.annotations.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;

@Test
public class ProfileServiceTest {

    @Tested
    private ProfileService profileService;

    @Injectable
    private OAuth2RestTemplate restTemplate;

    @Injectable
    private ProfileRepository profileRepository;

    public void getProfileShouldCallRestTemplate() {
        profileService.getProfile();

        new Verifications() {{
            restTemplate.getForObject("http://user-service/me", User.class);
        }};
    }

    @Test(expectedExceptions = UserNotFoundException.class)
    public void getProfileShouldThrowErrorWhenUserNotFound() {
        new Expectations() {{
            restTemplate.getForObject("http://user-service/me", User.class);
            result = null;
        }};

        profileService.getProfile();
    }

    public void getProfileShouldCallRepository() {
        profileService.getProfile();

        new Verifications() {{
            profileRepository.findByUserId(anyLong);
        }};
    }

    @Test(dataProviderClass = ProfileDataProvider.class, dataProvider = "profiles")
    public void getProfileShouldReturnFoundProfile(Profile profile) {
        new Expectations() {{
            profileRepository.findByUserId(anyLong);
            result = profile;
        }};

        assertThat(profileService.getProfile()).isEqualTo(profile);
    }

    @Test(expectedExceptions = ProfileNotFoundException.class)
    public void getProfileShouldThrowErrorWhenProfileNotFound() {
        new Expectations() {{
            profileRepository.findByUserId(anyLong);
            result = null;
        }};

        profileService.getProfile();
    }
}
