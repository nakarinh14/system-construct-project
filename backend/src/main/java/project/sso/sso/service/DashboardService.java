package project.sso.sso.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.sso.sso.entity.Course;
import project.sso.sso.entity.User;
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
        if(role.equals("admin")){
            return new DashboardResponse(role, courseRepository.findAll());
        } else {
            Set<Course> courses = userRepository.findByUsername(username).getCourses();
            List<Course> parsedCourses = new ArrayList<Course>(courses);
            return new DashboardResponse(role, parsedCourses);
        }
    }

    public Set<Course> getCourseByInstructor(String id){
        return courseRepository.findCoursesByInstructorId(id);
    }

    public boolean updateCourseByInfo(Long courseId, String info){
        Course course = courseRepository.findCourseById(courseId);
        if(course != null){
            course.setInfo(info); // This will update info, and update, not append row
            courseRepository.save(course);
            return true;
        }
        return false;
    }

}
