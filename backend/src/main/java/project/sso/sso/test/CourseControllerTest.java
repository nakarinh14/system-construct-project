package project.sso.sso.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import project.sso.sso.entity.Course;
import project.sso.sso.entity.User;
import project.sso.sso.service.DashboardService;
import project.sso.sso.service.UserService;

import java.util.Map;
import java.util.Set;

@RestController
public class CourseControllerTest {

    @Autowired
    UserService userService;
    @Autowired
    DashboardService dashboardService;

    @PostMapping("/api/test/student/course")
    public Set<Course> getStudentCourse(@RequestBody Map<String, Object> payload){
        String role = (String) payload.get("role");
        String username = (String) payload.get("username");

        Set<Course> courses = null;
        if(role.equals("student")){
            User user = userService.getUser(username);
            courses = user.getCourses();
        }
        return courses;
    }

    @PostMapping("/api/test/instructor/course")
    public Set<Course> getInstructorCourse(@RequestParam String instructorId){
        return dashboardService.getCourseByInstructor(instructorId);
    }

    @PostMapping("/api/test/instructor/update")
    public String updateCourseInfo(@RequestParam Long courseId, @RequestParam String info){
        boolean cond = dashboardService.updateCourseByInfo(courseId, info);
        return cond ? "success" : "failed";
    }


}
