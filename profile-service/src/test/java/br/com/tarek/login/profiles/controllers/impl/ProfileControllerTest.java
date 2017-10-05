package br.com.tarek.login.profiles.controllers.impl;

import br.com.tarek.login.profiles.entities.impl.Profile;
import br.com.tarek.login.profiles.exceptions.impl.ProfileNotFoundException;
import br.com.tarek.login.profiles.providers.ProfileDataProvider;
import br.com.tarek.login.profiles.services.IProfilerService;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.Verifications;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.*;

public class ProfileControllerTest {

    @Tested
    private ProfileController profileController;

    @Injectable
    private IProfilerService profileService;

    @Test
    public void getProfileShouldCallService() {
        profileController.getProfile();

        new Verifications() {{
            profileService.getProfile();
        }};
    }

    @Test(dataProviderClass = ProfileDataProvider.class, dataProvider = "profiles")
    public void getProfileShouldReturnFoundProfile(Profile profile) {
        new Expectations() {{
            profileService.getProfile();
            result = profile;
        }};

        assertThat(profileController.getProfile()).isEqualTo(profile);
    }

    @Test(expectedExceptions = ProfileNotFoundException.class)
    public void getProfileShouldThrowErrorIfProfileNotFound() {
        new Expectations() {{
            profileService.getProfile();
            result = null;
        }};

        profileController.getProfile();
    }
}
