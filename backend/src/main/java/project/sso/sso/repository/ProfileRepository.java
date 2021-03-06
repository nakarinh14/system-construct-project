package project.sso.sso.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.sso.sso.entity.Profile;
import project.sso.sso.entity.User;

import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {

    Optional<Profile> findById(Long id);

    Profile findByUser(User user);

    Profile findByFirstnameAndLastname(String firstname, String lastname);


}
