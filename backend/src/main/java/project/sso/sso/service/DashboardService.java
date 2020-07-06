package project.sso.sso.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.sso.sso.entity.Course;
import project.sso.sso.entity.User;
import project.sso.sso.model.DashboardInfoRequest;
import project.sso.sso.model.DashboardResponse;
import project.sso.sso.repository.CourseRepository;
import project.sso.sso.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class DashboardService {
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    UserRepository userRepository;

    public DashboardResponse getUserDashboard(String username){
        User user = userRepository.findByUsername(username);
        String role = user.getRole().getRole().getPermission();
        List<Course> courses = null;
        switch(role){
            case "admin":
                courses = courseRepository.findAll();
            case "instructor":
                Set<Course> coursesTeaches = userRepository.findByUsername(username).getCourseTeached();
                courses = new ArrayList<Course>(coursesTeaches); // Convert Set<Course> to List<Course>
            case "student":
                Set<Course> courseLearn = userRepository.findByUsername(username).getCourses();
                courses = new ArrayList<Course>(courseLearn); // Convert Set<Course> to List<Course>
        }
        return new DashboardResponse(role, courses);
    }

    public Set<Course> getCourseByInstructor(String username){
        return userRepository.findByUsername(username).getCourseTeached();
    }

    public boolean updateCourseByInfo(DashboardInfoRequest dashboardRequest){
        Course course = courseRepository.findCourseById(dashboardRequest.getCourseId());
        if(course != null){
            course.setInfo(dashboardRequest.getInfo()); // This will update info, and update, not append row
            courseRepository.save(course);
            return true;
        }
        return false;
    }

}
