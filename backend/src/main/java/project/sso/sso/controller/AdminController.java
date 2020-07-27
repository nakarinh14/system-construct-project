package project.sso.sso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import project.sso.sso.entity.Course;
import project.sso.sso.entity.Term;
import project.sso.sso.entity.User;
import project.sso.sso.model.*;
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
        return adminService.addUser(addUserRequest);
    }


    @GetMapping("/api/admin/users/course/{id}")
    List<Course> getAllCoursesByUsers(@PathVariable Long id, HttpSession httpSession){
        if(securityService.isAuthorized(httpSession, "admin")){
            return adminService.getAllCourses(id);
        }
        return null;
    }

    @GetMapping("/api/admin/users/instructor")
    List<User> getAllInstructor(HttpSession httpSession){
        if(securityService.isAuthorized(httpSession, "admin")){
            return adminService.getAllInstructor();
        }
        return null;
    }

    @PostMapping("/api/admin/users/remove/course")
    ValidateResponse removeCourseFromUser(@RequestBody RemoveUserCourseRequest removeUserCourseRequest, HttpSession httpSession) {
        if (securityService.isAuthorized(httpSession, "admin")) {
            return adminService.removeCourseFromUser(removeUserCourseRequest);
        }
        return null;
    }

    @PostMapping("/api/admin/courses/assign")
    ValidateResponse assignCourseToUser(@RequestBody AssignCourseRequest assignCourseRequest, HttpSession httpSession) {
        if (securityService.isAuthorized(httpSession, "admin")) {
            return adminService.assignCourseToUser(assignCourseRequest);
        }
        return null;
    }

    @PostMapping("/api/admin/add/course")
    ValidateResponse addNewCourse(@RequestBody AddNewCourseRequest addNewCourseRequest, HttpSession httpSession) {
        if (securityService.isAuthorized(httpSession, "admin")) {
            return adminService.addNewCourse(addNewCourseRequest);
        }
        return null;
    }

    @PostMapping("/api/admin/remove/user")
    ValidateResponse removeUser(@RequestBody RemoveUserRequest removeUserRequest, HttpSession httpSession) {
        if (securityService.isAuthorized(httpSession, "admin")) {
            return adminService.removeUser(removeUserRequest);
        }
        return null;
    }

    @PostMapping("/api/admin/add/term")
    ValidateResponse addNewTerm(@RequestBody AddNewTermRequest addNewTermRequest, HttpSession httpSession) {
        if (securityService.isAuthorized(httpSession, "admin")) {
            return adminService.addNewTerm(addNewTermRequest);
        }
        return null;
    }

    @GetMapping("/api/admin/get/term")
    List<Term> getAllTerm(HttpSession httpSession) {
        if (securityService.isAuthorized(httpSession, "admin")) {
            return adminService.getAllTerm();
        }
        return null;
    }

}
