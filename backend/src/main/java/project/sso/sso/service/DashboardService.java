package project.sso.sso.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.sso.sso.entity.Course;
import project.sso.sso.entity.Term;
import project.sso.sso.entity.User;
import project.sso.sso.model.*;
import project.sso.sso.repository.CourseRepository;
import project.sso.sso.repository.ProfileRepository;
import project.sso.sso.repository.TermRepository;
import project.sso.sso.repository.UserRepository;
import project.sso.sso.utils.ParseCourse;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class DashboardService {
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ProfileRepository profileRepository;
    @Autowired
    TermRepository termRepository;
    @Autowired
    ParseCourse parseCourse;


    public DashboardResponse getUserDashboard(DashboardRequest dashboardRequest, String username){
        User user = userRepository.findByUsername(username);
        String role = userRepository.getRoleOfUsername(username).toLowerCase();
        Long termId = dashboardRequest.getTermId() != null ? dashboardRequest.getTermId() : termRepository.getCurrentTermId();
        List<Course> courses = null;
        switch(role){
            case "admin":
                System.out.println("Got in admin");
                courses = courseRepository.findAll();
                break;
            case "instructor":
                System.out.println("Got in instructor");
                courses = courseRepository.findCoursesFromInstructorAndTerm(user.getId(), termId);
                break;
            case "student":
                System.out.println("Got in student");
                courses = courseRepository.findCoursesFromStudentAndTerm(username, termId);
                System.out.println(courses.size());
                break;
        }

        Term currentTerm = termRepository.findById(termId).get();
        return new DashboardResponse(
                role,
                parseCourse.parseCourse(courses, userRepository, profileRepository),
                termRepository.findAll(),
                currentTerm
        );
    }


    public Long getLatestTermId(){
        return termRepository.getCurrentTermId();
    }

    public List<User> getStudentsFromCourseId(Long courseId){
        return courseRepository.findStudentsByCourseIdAndInstructorId(courseId);
    }
    //
//    private List<Course> filterDashboard(List<Course> courses, Long term_id){
//        // If anyone find better efficient method, please do.
//        // Dont wanna use too much for loop.
//        if(term_id != null){ // term_id is null where all term is requested.
//            List<Course> filteredCourses = new ArrayList<>();
//            for(Course course : courses){
//                if(course.getTerm().getId().equals(term_id)) {
//                    filteredCourses.add(course);
//                }
//            }
//            return filteredCourses;
//        }
//        return courses;
//    }

//    public Set<Course> getCourseByInstructor(String username){
//        return courseRepository.findCoursesByInstructorId(user.getId());
//    }

    public boolean updateCourseByInfo(HttpSession session, DashboardUpdateInfoRequest dashboardRequest){
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
