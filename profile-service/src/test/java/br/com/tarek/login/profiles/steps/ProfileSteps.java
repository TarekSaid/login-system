package br.com.tarek.login.profiles.steps;

import br.com.tarek.login.profiles.ProfileApplication;
import br.com.tarek.login.profiles.entities.impl.Profile;
import cucumber.api.java8.En;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

@SpringBootTest(webEnvironment = DEFINED_PORT, properties = "spring.cloud.config.enabled:false")
@ContextConfiguration(classes = {ProfileApplication.class})
@ActiveProfiles("test")
public class ProfileSteps extends AbstractTestNGSpringContextTests implements En {

    @Autowired
    private TestRestTemplate restTemplate;

    private String username;

    private String password;

    private Profile profile;

    public ProfileSteps() {

        Given("^that I am logged in with \"([^\"]*)\" and password \"([^\"]*)\"$", (String user, String password) -> {
            this.username = user;
            this.password = password;
        });

        When("^I access my profile$", () -> {
            this.profile = restTemplate.withBasicAuth(username, password).getForObject("/", Profile.class);
        });

        Then("^I should see my name as \"([^\"]*)\" and business as \"([^\"]*)\" and website as \"([^\"]*)\"$",
                (String name, String business, String website) -> {
                    assertThat(profile.getName()).isEqualTo(name);
                    assertThat(profile.getBusinessType()).isEqualTo(business);
                    assertThat(profile.getWebsite()).isEqualTo(website);
                });
    }
}
