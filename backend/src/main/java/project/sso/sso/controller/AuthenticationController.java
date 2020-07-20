package project.sso.sso.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import project.sso.sso.model.AuthenticationResponse;
import project.sso.sso.model.ValidateResponse;
import project.sso.sso.model.AuthenticationRequest;
import project.sso.sso.service.SecurityService;

import javax.servlet.http.HttpSession;

@RestController
public class AuthenticationController {

    @Autowired
    private SecurityService securityService;

    @PostMapping("/api/auth/login")
    public AuthenticationResponse doLogin(@RequestBody AuthenticationRequest authenticationRequest, HttpSession session) {
        AuthenticationResponse response = securityService.authenticate(authenticationRequest);
        if(response.getStatus().equals("success")){
            session.setAttribute("username", authenticationRequest.getUsername());
        }
        return response;
    }

    @PostMapping("/api/auth/logout")
    public ValidateResponse doLogout(HttpSession session){
        securityService.logout(session);
        return new ValidateResponse("success");
    }

    @GetMapping("/api/auth/validate")
    public ValidateResponse validateLogin(HttpSession session){
        String status = securityService.isAuthorized(session) ? "success" : "failed";
        return new ValidateResponse(status);
    }

}
