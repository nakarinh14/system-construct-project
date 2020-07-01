package project.sso.sso.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.sso.sso.model.Course;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CourseListController {

    @GetMapping("/api/student/courses")
    public List<Course> getStudentCourses(){
        List<Course> tmp = new ArrayList<>();
        tmp.add(new Course(
                "101",
                "ICCS101",
                "Science",
                "3",
                "Prof. Gigadot",
                "30",
                "20",
                "10",
                "None",
                "very nice"));
        tmp.add(new Course(
                "101",
                "ICCS101",
                "Science",
                "3",
                "Prof. Gigadot",
                "30",
                "20",
                "10",
                "None",
                "very nice"));
        return tmp;
    }

}
