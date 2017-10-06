package br.com.tarek.login.users.steps;

import br.com.tarek.login.users.UsersApplication;
import br.com.tarek.login.users.config.AuthorizationServerConfig;
import br.com.tarek.login.users.entities.impl.User;
import cucumber.api.java8.En;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT, properties = "spring.cloud.config.enabled:false")
@ContextConfiguration(classes = {UsersApplication.class, AuthorizationServerConfig.class})
@ActiveProfiles("test")
public class UserSteps extends AbstractTestNGSpringContextTests implements En {

    @Autowired
    private TestRestTemplate restTemplate;

    private String username;

    private String password;

    private User user;

    public UserSteps() {

        Given("^that I am logged in with \"([^\"]*)\" and password \"([^\"]*)\"$", (String user, String password) -> {
            this.username = user;
            this.password = password;
        });

        When("^I fetch my information$", () -> {
            this.user = restTemplate.withBasicAuth(username, password).getForObject("/me", User.class);
        });

        Then("^I should see the username \"([^\"]*)\"$", (String expectedUser) -> {
            assertThat(user.getUsername()).isEqualTo(expectedUser);
        });
    }
}
