package project.sso.sso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import project.sso.sso.entity.Course;
import project.sso.sso.entity.User;
import project.sso.sso.service.CourseService;
import project.sso.sso.service.UserService;

import java.util.Set;

@RestController
public class CourseController {

    @Autowired
    UserService userService;
    @Autowired
    CourseService courseService;

    @PostMapping("/api/student/course")
    public Set<Course> getStudentCourse(@RequestParam String username,
                                   @RequestParam String role){
        Set<Course> courses = null;
        if(role.equals("student")){
            User user = userService.getUser(username);
            courses = user.getCourses();
        }
        return courses;
    }

    @PostMapping("/api/instructor/course")
    public Set<Course> getInstructorCourse(@RequestParam String instructorId){
        return courseService.getCourseByInstructor(instructorId);
    }

    @PostMapping("/api/instructor/update")
    public String updateCourseInfo(@RequestParam String courseId, @RequestParam String info){
        boolean cond = courseService.updateCourseByInfo(courseId, info);
        return cond ? "success" : "failed";
    }
}
