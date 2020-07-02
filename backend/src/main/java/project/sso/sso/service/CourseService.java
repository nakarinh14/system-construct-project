package project.sso.sso.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.sso.sso.entity.Course;
import project.sso.sso.repository.CourseRepository;

import java.util.Set;

@Service
public class CourseService {
    @Autowired
    CourseRepository courseRepository;

    public Set<Course> getCourseByInstructor(String id){
        return courseRepository.findCoursesByInstructorId(id);
    }

    public boolean updateCourseByInfo(String courseId, String info){
        Course course = courseRepository.findCourseById(courseId);
        if(course != null){
            course.setInfo(info); // This will update info, and update, not append row
            courseRepository.save(course);
            return true;
        }
        return false;
    }
}
