package project.sso.sso.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.sso.sso.entity.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

}
