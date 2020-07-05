package project.sso.sso.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import project.sso.sso.entity.Role;
import project.sso.sso.entity.User;
import project.sso.sso.model.AuthenticationRequest;
import project.sso.sso.model.AuthenticationResponse;
import project.sso.sso.repository.RoleRepository;
import project.sso.sso.repository.UserRepository;
import project.sso.sso.security.JwtUtil;
import project.sso.sso.security.MyUserDetailsService;

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

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    public boolean isAuthorized(AuthenticationRequest authenticationRequest) throws Exception {
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getUsername(), authenticationRequest.getPassword()
                    )
            );
            return true;
        } catch (BadCredentialsException e){
            throw new Exception("Incorrect username or password", e);
        }
    }

    public boolean authenticate(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && Objects.equals(user.getPassword(), password)) {
            session.setAttribute("username", username);
            session.setAttribute("role", user.getRole().getRole());
            return true;
        } else {
            return false;
        }
    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) throws Exception {
        if(isAuthorized(authenticationRequest)) {
            final UserDetails USER_DETAILS = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

            final String JWT = jwtUtil.generateToken(USER_DETAILS);

            return new AuthenticationResponse(JWT);
        }
        return new AuthenticationResponse("403");
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
