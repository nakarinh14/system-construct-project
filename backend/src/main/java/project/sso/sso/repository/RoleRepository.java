package project.sso.sso.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.sso.sso.entity.Role;
import project.sso.sso.entity.User;
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByUser(User user);


}
