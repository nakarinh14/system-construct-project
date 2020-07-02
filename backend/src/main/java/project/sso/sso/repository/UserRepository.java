package project.sso.sso.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.sso.sso.entity.Role;
import project.sso.sso.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

     Optional<User> findById(Long id);

     User findByUsername(String username);

     boolean existsByUsername(String username);

     List<User> findByRole(Role role);



}
