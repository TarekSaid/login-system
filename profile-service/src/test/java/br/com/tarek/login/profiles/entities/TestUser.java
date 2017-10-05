package br.com.tarek.login.profiles.entities;

import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;

public class TestUser extends ResourceOwnerPasswordResourceDetails {

    public TestUser() {
        setAccessTokenUri("/oauth/token");
        setClientId("webClient");
        setClientSecret("secret");
        setUsername("user");
        setPassword("password");
    }
}
