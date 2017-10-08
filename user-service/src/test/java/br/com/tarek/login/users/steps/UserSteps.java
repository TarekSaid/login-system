package br.com.tarek.login.users.steps;

import br.com.tarek.login.users.UsersApplication;
import br.com.tarek.login.users.entities.impl.User;
import cucumber.api.java8.En;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ContextConfiguration(classes = UsersApplication.class)
@ActiveProfiles("test")
public class UserSteps extends AbstractTestNGSpringContextTests implements En {

    @Autowired
    private TestRestTemplate restTemplate;

    private String username;

    private String password;

    private ResponseEntity<User> response;

    public UserSteps() {

        Given("^that I am logged in with \"([^\"]*)\" and password \"([^\"]*)\"$", (String user, String password) -> {
            this.username = user;
            this.password = password;
        });

        When("^I fetch my information$", () -> {
            this.response = restTemplate.withBasicAuth(username, password).getForEntity("/me", User.class);
        });

        Then("^I should see the username \"([^\"]*)\"$", (String expectedUser) -> {
            User user = response.getBody();
            assertThat(user.getUsername()).isEqualTo(expectedUser);
        });

        Given("^that I am not logged in$", () -> {
            this.username = null;
            this.password = null;
        });

        Then("^I should see the status (\\d+)$", (Integer status) -> {
            assertThat(response.getStatusCode().value()).isEqualTo(status);
        });
    }
}
