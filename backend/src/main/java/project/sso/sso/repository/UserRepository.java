package project.sso.sso.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import project.sso.sso.entity.Course;
import project.sso.sso.entity.Role;
import project.sso.sso.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


     User findByUsername(String username);

     boolean existsByUsername(String username);

     List<User> findAllByRole(Role role);

     @Query("SELECT u.role.role FROM User u WHERE u.username = ?1")
     String getRoleOfUsername(String username);

     @Query("SELECT u.role.role FROM User u WHERE u.id = ?1")
     String getRoleOfId(Long id);

}
