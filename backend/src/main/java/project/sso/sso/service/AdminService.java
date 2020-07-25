package project.sso.sso.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.sso.sso.entity.Course;
import project.sso.sso.entity.Profile;
import project.sso.sso.entity.Role;
import project.sso.sso.entity.User;
import project.sso.sso.misc.RoleType;
import project.sso.sso.model.*;
import project.sso.sso.repository.*;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProfileRepository profileRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    TermRepository termRepository;


    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public List<Course> getAllCourses(Long id){
        String role = userRepository.getRoleOfId(id).toLowerCase();
        if(role.equals("student")){
            return courseRepository.findCourseByStudentId(id);
        } else if (role.equals("instructor")){
            return courseRepository.findAllByInstructorId(id);
        }
        return null;
    }

    public ValidateResponse addUser(AddUserRequest addUserRequest) {
        if (!userRepository.existsByUsername(addUserRequest.getUsername())) {
            // Set profile
            Profile profile = new Profile();
            profile.setFirstname(addUserRequest.getFirstname());
            profile.setLastname(addUserRequest.getLastname());
            profile.setTitle(addUserRequest.getTitle());

            // Set user
            User user = new User();
            user.setUsername(addUserRequest.getUsername());
            user.setPassword(addUserRequest.getPassword());

            user.setProfile(profile);
            profile.setUser(user);

            Role role = roleRepository.findByRoleEquals(RoleType.valueOf(addUserRequest.getRole().toUpperCase()));
            user.setRole(role);
            role.getUser().add(user);

            userRepository.save(user);
            roleRepository.save(role);


            return new ValidateResponse("success");
        } else {
            return new ValidateResponse("fail");
        }
    }

    public ValidateResponse removeCourse(RemoveCourseRequest removeCourseRequest) {
        User target = userRepository.findByUsername(removeCourseRequest.getUsername());
        Course targetCourse = courseRepository.findCourseById(removeCourseRequest.getRemoveCourseID());
        if(target != null){
            target.getCourses().remove(targetCourse);
            targetCourse.getStudents().remove(target);
            userRepository.save(target);
            courseRepository.save(targetCourse);
            return new ValidateResponse("Success");
        }
        return new ValidateResponse("Fail");
    }

    public ValidateResponse assignCourse(AssignCourseRequest assignCourseRequest) {
        User user = userRepository.findByUsername(assignCourseRequest.getUsername());
        Course course = courseRepository.findCourseById(assignCourseRequest.getAddCourseID());
        if (user != null) {
            course.getStudents().add(user);
            user.getCourses().add(course);
            userRepository.save(user);
            courseRepository.save(course);
            return new ValidateResponse("Success");
        }
        return new ValidateResponse("Fail");
    }

    public ValidateResponse addNewCourse(AddNewCourseRequest addNewCourseRequest) {
        Course newCourse = new Course(addNewCourseRequest);
        courseRepository.save(newCourse);
        if (!courseRepository.existsByCourseId(newCourse.getCourseId())) {
            return new ValidateResponse("Fail");
        }
        return new ValidateResponse("Success");
    }

}