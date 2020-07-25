package project.sso.sso.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import project.sso.sso.entity.Course;
import project.sso.sso.entity.User;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    Course findCourseById(Long id);

    @Query("SELECT c FROM Course c WHERE c.term.id = ?1")
    List<Course> findCourseByTermId(Long id);

    @Query("SELECT c FROM Course c LEFT JOIN c.students s WHERE s.username = ?1 AND c.term.id = ?2")
    List<Course> findCoursesFromStudentAndTerm(String username, Long term_id);

    @Query("SELECT c FROM Course c WHERE c.instructorId = ?1 AND c.term.id = ?2")
    List<Course> findCoursesFromInstructorAndTerm(Long instructor_id, Long term_id);

    @Query("SELECT c.students FROM Course c WHERE c.id = ?1")
    List<User> findStudentsByCourseIdAndInstructorId(Long courseId);

    List<Course> findAllByInstructorId(Long id);

    @Query("SELECT c FROM Course c LEFT JOIN c.students s WHERE s.id = ?1")
    List<Course> findCourseByStudentId(Long id);

    boolean existsByCourseId(String courseID);
}
