package project.sso.sso.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.sso.sso.entity.Role;
import project.sso.sso.entity.User;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByUser(User user);


}
