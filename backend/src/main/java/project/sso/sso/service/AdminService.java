package project.sso.sso.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.sso.sso.entity.Profile;
import project.sso.sso.entity.Role;
import project.sso.sso.entity.User;
import project.sso.sso.model.AddUserRequest;
import project.sso.sso.model.ValidateResponse;
import project.sso.sso.repository.ProfileRepository;
import project.sso.sso.repository.RoleRepository;
import project.sso.sso.repository.UserRepository;

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

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public ValidateResponse addUser(AddUserRequest addUserRequest) {
        if (!userRepository.existsByUsername(addUserRequest.getUsername())) {
            // Set profile
            Profile profile = new Profile();
            profile.setFirstname(addUserRequest.getFirstname());
            profile.setLastname(addUserRequest.getLastname());
            profile.setTitle(addUserRequest.getTitle());
            profileRepository.save(profile);
            //Set role
            Role role = new Role();

            // Set user
            User user = new User();
            user.setUsername(addUserRequest.getUsername());
            user.setPassword(addUserRequest.getPassword());
            user.setProfile(profile);
            Optional<Role> roleName = roleRepository.findById(addUserRequest.getRole());
            user.setRole(roleName.get());
            profile.setUser(user);

            userRepository.save(user);
            return new ValidateResponse("Success");
        } else {
            return new ValidateResponse("Fail");
        }
    }
}


