//package project.sso.sso.test;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import project.sso.sso.model.AuthenticationRequest;
//import project.sso.sso.model.AuthenticationResponse;
//import project.sso.sso.security.JwtUtil;
//import project.sso.sso.security.MyUserDetailsService;
//import project.sso.sso.service.SecurityService;
//
//@RestController
//public class AuthenticateController {
//
//    @Autowired
//    SecurityService securityService;
//
//    @PostMapping("/api/test/jwtauth")
//    public AuthenticationResponse createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
//        return securityService.authenticate(authenticationRequest);
//    }
//}
