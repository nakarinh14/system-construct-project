package project.sso.sso.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.sso.sso.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

     User findByUsername(String username);

}
