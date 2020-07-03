package project.sso.sso.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import project.sso.sso.entity.Course;
import project.sso.sso.entity.User;
import project.sso.sso.repository.CourseRepository;
import project.sso.sso.repository.UserRepository;

import java.util.HashSet;
import java.util.Set;


@RestController
public class UserControllerTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CourseRepository courseRepository;

    @GetMapping("/api/student/test")
    public User addUser() {
        Set<Course> t = new HashSet<>();
        Course courses = new Course();
        courses.setCapacity(10);
        courses.setDate("10");
        courses.setInfo("testing");
        courses.setInstructorId("idk");
        courses.setRegistered(10);
        courses.setSection("2");
        t.add(courses);
        courseRepository.save(courses);

        User user = new User();
        user.setUsername("test");
        user.setPassword("test");
        user.setCourses(t);
//        Role role = new Role();
//        role.setRole("Admin");
//        user.setRole(role);
        User savedUser = userRepository.save(user);

        return savedUser;
    }
}
