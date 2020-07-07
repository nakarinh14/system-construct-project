package project.sso.sso.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.sso.sso.entity.Course;
import project.sso.sso.entity.User;
import project.sso.sso.model.DashboardInfoRequest;
import project.sso.sso.model.DashboardResponse;
import project.sso.sso.repository.CourseRepository;
import project.sso.sso.repository.ProfileRepository;
import project.sso.sso.repository.UserRepository;
import project.sso.sso.utils.ParseCourse;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class DashboardService {
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ProfileRepository profileRepository;
    @Autowired
    ParseCourse parseCourse;

    public DashboardResponse getUserDashboard(String username){
        User user = userRepository.findByUsername(username);
        String role = user.getRole().getRole().getPermission();
        List<Course> courses = null;
        switch(role){
            case "admin":
                courses = courseRepository.findAll();
                break;
            case "instructor":
                courses = courseRepository.findCoursesByInstructorId(user.getId());
                break;
            case "student":
                Set<Course> courseLearn = userRepository.findByUsername(username).getCourses();
                courses = new ArrayList<>(courseLearn);
                break;
        }
        return new DashboardResponse(
                role,
                parseCourse.parseCourse(courses, userRepository, profileRepository)
        );
    }

//    public Set<Course> getCourseByInstructor(String username){
//        return courseRepository.findCoursesByInstructorId(user.getId());
//    }

    public boolean updateCourseByInfo(HttpSession session, DashboardInfoRequest dashboardRequest){
        Course course = courseRepository.findCourseById(dashboardRequest.getCourseId());
        Long userId = userRepository.findByUsername((String) session.getAttribute("username")).getId();
        if(course != null){
            if(course.getInstructorId().equals(userId)) {
                course.setInfo(dashboardRequest.getInfo()); // This will update info, and update, not append row
                courseRepository.save(course);
                return true;
            }
        }
        return false;
    }

}
