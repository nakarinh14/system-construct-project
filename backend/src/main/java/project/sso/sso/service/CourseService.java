package project.sso.sso.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.sso.sso.entity.Course;
import project.sso.sso.entity.User;
import project.sso.sso.repository.CourseRepository;
import project.sso.sso.repository.UserRepository;

import java.util.Set;

@Service
public class CourseService {
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    UserRepository userRepository;

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

//    public void addCourse(Long courseId,
//                          String username,
//                          String info,
//                          int capacity,
//                          int registered,
//                          int section){
//        Course course = new Course();
//
//        course.setInfo(info);
//        course.setCapacity(capacity);
//        course.setRegistered(registered);
//        course.setSection(section);
//        course.setInstructorId();
//        course.set
//
//    }
//
//    public void addInstructor(Long courseId, ){
//
//    }
//
}
