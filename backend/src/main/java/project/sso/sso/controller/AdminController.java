package project.sso.sso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import project.sso.sso.entity.Course;
import project.sso.sso.entity.User;
import project.sso.sso.model.AddUserRequest;
import project.sso.sso.model.AddCourseRequest;
import project.sso.sso.model.RemoveCourseRequest;
import project.sso.sso.model.ValidateResponse;
import project.sso.sso.service.AdminService;
import project.sso.sso.service.DashboardService;
import project.sso.sso.service.SecurityService;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class AdminController {

    @Autowired
    SecurityService securityService;

    @Autowired
    AdminService adminService;

    @Autowired
    DashboardService dashboardService;

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

    @GetMapping("/api/admin/users/course/{id}")
    List<Course> getAllCoursesByUsers(@PathVariable Long id, HttpSession httpSession){
        if(securityService.isAuthorized(httpSession, "admin")){
            return adminService.getAllCourses(id);
        }
        return null;
    }

    @GetMapping("/api/admin/users/remove/course")
    ValidateResponse  removeCourseFromUser(@RequestBody RemoveCourseRequest removeCourseRequest, HttpSession httpSession){
        if(securityService.isAuthorized(httpSession, "admin")){
            return adminService.removeCourse(removeCourseRequest);
        }
        return null;
    }

    @GetMapping("/api/admin/users/add/course")
    ValidateResponse assignCourseToUser(@RequestBody AddCourseRequest addCourseRequest, HttpSession httpSession) {
        if (securityService.isAuthorized(httpSession,"admin")) {
            return adminService.addCourse(addCourseRequest);
        }
        return null;
    }


}
