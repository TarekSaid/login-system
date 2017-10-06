package br.com.tarek.login.profiles.clients;

import br.com.tarek.login.profiles.entities.impl.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("user-service")
public interface UserClient {

    @RequestMapping("/me")
    User getLoggedUser();
}
