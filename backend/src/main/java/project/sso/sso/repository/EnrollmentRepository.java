package project.sso.sso.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import project.sso.sso.entity.Course;
import project.sso.sso.entity.Enrollment;

import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {




    @Query("SELECT * FROM Enrollment e LEFT JOIN Course ON ")
    List<Course> getCourseByUsername();
}
