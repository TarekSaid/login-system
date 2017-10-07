package br.com.tarek.login.profiles.resources;

import br.com.tarek.login.profiles.entities.impl.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "user-service")
public interface UserResource {

    @RequestMapping("/me")
    User getLoggedUser();
}
