package project.sso.sso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import project.sso.sso.entity.User;
import project.sso.sso.model.*;
import project.sso.sso.repository.UserRepository;
import project.sso.sso.service.DashboardService;
import project.sso.sso.service.SecurityService;
import project.sso.sso.service.UserService;

import javax.servlet.http.HttpSession;
import java.util.List;

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

    @GetMapping("/api/dashboard")
    public DashboardResponse getDashboard(@RequestParam Long id, HttpSession session){
        if(securityService.isAuthorized(session)){
            return dashboardService.getUserDashboard(id, (String) session.getAttribute("username"));
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

    @GetMapping("/api/dashboard/enrolled/{courseId}")
    public List<User> getEnrolledStudents(@PathVariable Long courseId, HttpSession session){
        return dashboardService.getStudentsFromCourseId(courseId);
    }

}
