package project.sso.sso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import project.sso.sso.entity.User;
import project.sso.sso.model.AddUserRequest;
import project.sso.sso.model.ValidateResponse;
import project.sso.sso.service.AdminService;
import project.sso.sso.service.SecurityService;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class AdminController {

    @Autowired
    SecurityService securityService;

    @Autowired
    AdminService adminService;

    @GetMapping("/api/admin/users")
    List<User> getAllUsers(HttpSession httpSession){
        if(securityService.isAuthorized(httpSession, "admin")){
            return adminService.getAllUsers();
        }
        return null;
    }

    @PostMapping("/api/admin/add/user")
    ValidateResponse addUser(@RequestBody AddUserRequest addUserRequest, HttpSession httpSession){
        if(securityService.isAuthorized(httpSession, "admin")){
            return adminService.addUser(addUserRequest);
        }
        return null;
    }
}
