package br.com.tarek.login.profiles.services.impl;

import br.com.tarek.login.profiles.entities.impl.Profile;
import br.com.tarek.login.profiles.entities.impl.User;
import br.com.tarek.login.profiles.providers.ProfileDataProvider;
import br.com.tarek.login.profiles.repositories.ProfileRepository;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.Verifications;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
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

    @Test(dataProviderClass = ProfileDataProvider.class, dataProvider = "users")
    public void getProfileShouldCallRepository(User user) {
        new Expectations() {{
            restTemplate.getForObject("http://user-service/me", User.class);
            result = user;
        }};

        profileService.getProfile();

        new Verifications() {{
            profileRepository.findByUserId(user.getId());
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

    public void getProfileShouldReturnNullWhenProfileNotFound() {
        new Expectations() {{
            profileRepository.findByUserId(anyLong);
            result = new IncorrectResultSizeDataAccessException(1);
        }};

        assertThat(profileService.getProfile()).isNull();
    }
}
