package project.sso.sso.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.sso.sso.entity.Course;
import project.sso.sso.entity.Profile;
import project.sso.sso.entity.Role;
import project.sso.sso.entity.User;
import project.sso.sso.misc.RoleType;
import project.sso.sso.model.AddUserRequest;
import project.sso.sso.model.ValidateResponse;
import project.sso.sso.repository.*;

import java.util.List;
import java.util.Optional;

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

    public ValidateResponse addUser(AddUserRequest addUserRequest){
        // Set profile
        Profile profile = new Profile();
        profile.setFirstname(addUserRequest.getFirstname());
        profile.setLastname(addUserRequest.getLastname());
        profile.setTitle(addUserRequest.getTitle());
        //Set role
        Role role = new Role();
        role.setRole(RoleType.valueOf(addUserRequest.getRole()));

        // Set user
        User user = new User();
        user.setUsername(addUserRequest.getUsername());
        user.setPassword(addUserRequest.getPassword());
        user.setProfile(profile);
        user.setRole(role);
        return null;
    }
}