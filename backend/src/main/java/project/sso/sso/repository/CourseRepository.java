package project.sso.sso.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.sso.sso.entity.Course;

import java.util.Set;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    Set<Course> findCoursesByInstructorId(String id);

    Course findCourseById(Long id);

}
