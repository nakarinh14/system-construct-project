package project.sso.sso.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import project.sso.sso.entity.Profile;
import project.sso.sso.entity.User;
import project.sso.sso.repository.ProfileRepository;
import project.sso.sso.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProfileRepository profileRepository;


    public User authenticate(String username, String password){

        User user = userRepository.findByUsername(username);

        if(user != null){
            if(user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public User getUser(String username){
        return userRepository.findByUsername(username);
    }

    public User addUser(String username, String password, String firstname, String lastname) {
        User user = new User();
        Profile profile = new Profile();

        user.setUsername(username);
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        user.setPassword(hashedPassword);
        userRepository.save(user);

        profile.setFirstname(firstname);
        profile.setLastname(lastname);
        profile.setUser(user);
        profileRepository.save(profile);

        return getUser(username);
    }
}
