package project.sso.sso.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.sso.sso.entity.Role;
import project.sso.sso.entity.User;
import project.sso.sso.repository.RoleRepository;
import project.sso.sso.repository.UserRepository;

import javax.servlet.http.HttpSession;
import java.util.Objects;
import java.util.Optional;

@Service
public class SecurityService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private HttpSession session;

    public String getCurrentUsername() {
        Object usernameObject = session.getAttribute("username");
        return (String) usernameObject;
    }

    public boolean isAuthorized() {
        String username = getCurrentUsername();
        return userRepository.existsByUsername(username);
    }

    public boolean authenticate(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && Objects.equals(user.getPassword(), password)) {
            session.setAttribute("username", username);
            Long id = user.getId();
            session.setAttribute("sessionId", id);
            session.setAttribute("role", user.getRole().getRole().toString());
            return true;
        } else {
            return false;
        }
    }

    public Optional<User> getUser(Long id){
        return userRepository.findById(id);
    }

    public User getUser(String username){
        return userRepository.findByUsername(username);
    }

    public Role getRole(User user){
        return roleRepository.findByUser(user);
    }

    public Role getRole(String username){
        User user = userRepository.findByUsername(username);
        return roleRepository.findByUser(user);
    }

    public void logout() {
        session.removeAttribute("username");
        session.invalidate();
    }
}
