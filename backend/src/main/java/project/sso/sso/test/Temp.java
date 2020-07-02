package project.sso.sso.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.sso.sso.entity.User;
import project.sso.sso.repository.UserRepository;

@Service
public class Temp {
    // This is created just for testing only.
    @Autowired
    private UserRepository userRepository;

    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public void addUsername(String username, String password){
        User usr = new User();
        usr.setUsername(username);
        usr.setPassword(password);
        userRepository.save(usr);
    }


}
