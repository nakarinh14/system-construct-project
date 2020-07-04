package project.sso.sso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import project.sso.sso.service.SecurityService;

@Controller
public class HomeController {

    @Autowired
    private SecurityService securityService;

    @GetMapping("/")
    public String home(Model model) {
        if (!securityService.isAuthorized()) {
            return "redirect:/login";
        }
        model.addAttribute("username", securityService.getCurrentUsername());
        return "home";
    }

}

