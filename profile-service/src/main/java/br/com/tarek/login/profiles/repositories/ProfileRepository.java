package br.com.tarek.login.profiles.repositories;

import br.com.tarek.login.profiles.entities.impl.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {

    Profile findByUserId(Long userId);
}
