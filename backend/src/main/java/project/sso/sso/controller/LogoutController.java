package project.sso.sso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import project.sso.sso.service.SecurityService;

public class LogoutController {

    @Autowired
    private SecurityService securityService;

    @GetMapping("/logout")
    public String logout() {
        securityService.logout();
        return "redirect:/login";
    }

}

