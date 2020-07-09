package project.sso.sso.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.sso.sso.entity.Profile;
import project.sso.sso.entity.Role;
import project.sso.sso.entity.User;
import project.sso.sso.model.AuthenticationRequest;
import project.sso.sso.model.AuthenticationResponse;
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

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        String username = authenticationRequest.getUsername();
        String password = authenticationRequest.getPassword();
        User user = userRepository.findByUsername(username);
        if(user != null && Objects.equals(user.getPassword(), password)){
            Profile profile = user.getProfile();
            AuthenticationResponse response = new AuthenticationResponse();
            response.setStatus("success");
            response.setUsername(username);
            response.setFirstname(profile.getFirstname());
            response.setLastname(profile.getLastname());
            response.setRole(user.getRole().getRole().getPermission().toLowerCase());
            return response;
        }
        return new AuthenticationResponse("failed",null,null,null, null);
    }

    public boolean isAuthorized(HttpSession session){
        return session.getAttribute("username") != null;
    }

    public boolean isAuthorized(HttpSession session, String role){
        if(isAuthorized(session)){
            String username = (String) session.getAttribute("username");
            return getRole(username).equals(role);
        }
        return false;
    }

    private String getRole(String username){
        return userRepository.findByUsername(username).getRole().getRole().getPermission();
    }

    public Optional<User> getUser(Long id){
        return userRepository.findById(id);
    }

    public Role getRole(User user){
        return roleRepository.findByUser(user);
    }

    public void logout(HttpSession session) {
        session.removeAttribute("username");
        session.invalidate();
    }


// ================================ JWT Authentication: May not be used... =========================
// ================================ I realized normal session works too =====================

//
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    @Autowired
//    private MyUserDetailsService userDetailsService;
//
//    @Autowired
//    private JwtUtil jwtUtil;
//
//    public boolean isAuthorized(AuthenticationRequest authenticationRequest) throws Exception {
//        try{
//            authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(
//                            authenticationRequest.getUsername(), authenticationRequest.getPassword()
//                    )
//            );
//            return true;
//        } catch (BadCredentialsException e){
//            throw new Exception("Incorrect username or password", e);
//        }
//    }
//
//    public AuthenticationResponse authenticate_jwt(AuthenticationRequest authenticationRequest) throws Exception {
//        if(isAuthorized(authenticationRequest)) {
//            final UserDetails USER_DETAILS = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
//
//            final String JWT = jwtUtil.generateToken(USER_DETAILS);
//
//            return new AuthenticationResponse(JWT);
//        }
//        return new AuthenticationResponse("403");
//    }
//

}
