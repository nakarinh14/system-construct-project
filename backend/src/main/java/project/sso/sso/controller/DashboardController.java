package project.sso.sso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import project.sso.sso.model.*;
import project.sso.sso.repository.UserRepository;
import project.sso.sso.service.DashboardService;
import project.sso.sso.service.SecurityService;
import project.sso.sso.service.UserService;

import javax.servlet.http.HttpSession;

@RestController
public class DashboardController {

    @Autowired
    UserService userService;

    @Autowired
    SecurityService securityService;

    @Autowired
    DashboardService dashboardService;

    @Autowired
    UserRepository userRepository;
    // Replace all dashboard into one response {role:..., course:...}, to be validate at frontend again.

    @PostMapping("/api/dashboard")
    public DashboardResponse getDashboard(@RequestBody DashboardRequest dashboardRequest, HttpSession session){
        if(securityService.isAuthorized(session)){
            return dashboardService.getUserDashboard(dashboardRequest, (String) session.getAttribute("username"));
        } else {
            // If not authenticated, return response with empty attributes.
            return new DashboardResponse();
        }
    }

    @PostMapping("/api/dashboard/update")
    public ValidateResponse updateDashboard(@RequestBody DashboardUpdateInfoRequest dashboardRequest, HttpSession session){
        if(securityService.isAuthorized(session,"instructor")){
            // Update info with function from DashboardService with DashboardInfoRequest
            if(dashboardService.updateCourseByInfo(session, dashboardRequest)){
                return new ValidateResponse("success");
            }
        }
        return new ValidateResponse("failed");
    }

    @GetMapping("/api/dashboard/latest")
    public Long getLatest(){
        return dashboardService.getLatestTermId();
    }

// =========== Decide to combine instructor/student into one with /api/dashboard =============

//    // Return all course for student, if the session username belongs to student
//    @PostMapping("/api/dashboard/student")
//    public Set<Course> getStudentCourse(HttpSession session){
//        Set<Course> courses = null;
//        if(role.equals("student")){
//            User user = userService.getUser(username);
//            courses = user.getCourses();
//        }
//        return courses;
//    }
//    // Return all course for instructor, if the session username belongs to student
//    @PostMapping("/api/dashboard/instructor")
//    public Set<Course> getInstructorCourse(@RequestParam String instructorId){
//        return dashboardService.getCourseByInstructor(instructorId);
//    }
}
