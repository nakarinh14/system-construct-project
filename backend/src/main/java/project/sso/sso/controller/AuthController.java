package project.sso.sso.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.sso.sso.model.AuthenticateResponse;
import project.sso.sso.model.AuthenticationRequest;
import project.sso.sso.model.AuthenticationResponse;
import project.sso.sso.service.SecurityService;

import javax.servlet.http.HttpSession;

@RestController
public class AuthController {

    @Autowired
    private SecurityService securityService;

    @PostMapping("/api/auth/login")
    public AuthenticateResponse doLogin(@RequestBody AuthenticationRequest authenticationRequest, HttpSession session) {
        if (securityService.authenticate(authenticationRequest)) {
            session.setAttribute("username", authenticationRequest.getUsername());
            return new AuthenticateResponse("success");
        } else {
            return new AuthenticateResponse("failed");
        }
    }

    @PostMapping("/api/auth/logout")
    public void doLogout(HttpSession session){
        session.removeAttribute("username");
        session.invalidate();
    }

}
