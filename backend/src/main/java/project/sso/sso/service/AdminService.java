package project.sso.sso.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import project.sso.sso.entity.*;
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

    public List<User> getAllInstructor(){
        return userRepository.findAllByRole(roleRepository.findByRoleEquals(RoleType.INSTRUCTOR));
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
            String hashedPassword = BCrypt.hashpw(addUserRequest.getPassword(), BCrypt.gensalt());
            user.setPassword(hashedPassword);

            user.setProfile(profile);

            profile.setUser(user);
            System.out.println(addUserRequest.getRole().toUpperCase());
            Role role = roleRepository.findByRole(RoleType.valueOf(addUserRequest.getRole().toUpperCase()));
            user.setRole(role);
            role.getUser().add(user);

            userRepository.save(user);
            roleRepository.save(role);
            profileRepository.save(profile);

            return new ValidateResponse("success");
        } else {
            return new ValidateResponse("fail");
        }
    }

    public ValidateResponse removeUser(RemoveUserRequest removeUserRequest) {
        User target = userRepository.findByUsername(removeUserRequest.getUsername());
        Profile profile = profileRepository.findByUser(target);
        Role role = roleRepository.findByUser(target);

        if (target.getRole().getRole().getPermission().equals("student")) {
            List<Course> userCourse = courseRepository.findCourseByStudentId(target.getId());
            for (Course c : userCourse) {
                target.getCourses().remove(c);
                c.getStudents().remove(target);
                userRepository.save(target);
                courseRepository.save(c);
            }
            role.getUser().remove(target);
            roleRepository.save(role);
            profileRepository.delete(profile);
            userRepository.delete(target);
        }
        if (!userRepository.existsByUsername(removeUserRequest.getUsername())) {
            return new ValidateResponse("success");
        }
        return new ValidateResponse("fail");
    }

    public ValidateResponse removeCourseFromUser(RemoveUserCourseRequest removeUserCourseRequest) {
        User target = userRepository.findByUsername(removeUserCourseRequest.getUsername());
        Course targetCourse = courseRepository.findCourseById(removeUserCourseRequest.getRemoveCourseID());
        if (target != null && targetCourse != null) {
            target.getCourses().remove(targetCourse);
            targetCourse.getStudents().remove(target);
            userRepository.save(target);
            courseRepository.save(targetCourse);
            return new ValidateResponse("success");
        }
        return new ValidateResponse("fail");
    }

    public ValidateResponse assignCourseToUser(AssignCourseRequest assignCourseRequest) {
        Role role = userRepository.findByUsername(assignCourseRequest.getUsername()).getRole();
        switch (role.getRole()) {
            case STUDENT:
                return assignCourseToStudent(assignCourseRequest);
            case INSTRUCTOR:
                return assignCourseToInstructor(assignCourseRequest);
            default:
                return new ValidateResponse("Fail");
        }
    }

    public ValidateResponse assignCourseToInstructor(AssignCourseRequest assignCourseRequest) {
        User user = userRepository.findByUsername(assignCourseRequest.getUsername());
        Course course = courseRepository.findCourseById(assignCourseRequest.getAddCourseID());
        if (user != null && course != null) {
            course.setInstructorId(user.getId());
            user.getCourses().add(course);
            userRepository.save(user);
            courseRepository.save(course);
            return new ValidateResponse("Success");
        }
        return new ValidateResponse("Fail");
    }

    public ValidateResponse assignCourseToStudent(AssignCourseRequest assignCourseRequest) {
        User user = userRepository.findByUsername(assignCourseRequest.getUsername());
        Course course = courseRepository.findCourseById(assignCourseRequest.getAddCourseID());
        if (user != null && course != null) {
            course.getStudents().add(user);
            user.getCourses().add(course);
            userRepository.save(user);
            courseRepository.save(course);
            return new ValidateResponse("success");
        }
        return new ValidateResponse("fail");
    }

    public ValidateResponse addNewCourse(AddNewCourseRequest addNewCourseRequest) {
        Course newCourse = new Course(addNewCourseRequest);
        Term term = termRepository.findTermById(addNewCourseRequest.getTermId());
        newCourse.setTerm(term);
        term.getCourses().add(newCourse);
        courseRepository.save(newCourse);
        termRepository.save(term);
        if (!courseRepository.exists(Example.of(newCourse))) {
            return new ValidateResponse("fail");
        }
        return new ValidateResponse("success");
    }

    public ValidateResponse addNewTerm(AddNewTermRequest addNewTermRequest) {
        Term term = new Term(addNewTermRequest);
        termRepository.save(term);
        if (termRepository.existsById(term.getId())) {
            return new ValidateResponse("success");
        }
        return new ValidateResponse("fail");
    }

    public List<Term> getAllTerm() {
        return termRepository.findAll();
    }

}