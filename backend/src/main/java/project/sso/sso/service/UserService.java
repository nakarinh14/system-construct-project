package project.sso.sso.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.sso.sso.entity.User;
import project.sso.sso.model.UserPostForm;
import project.sso.sso.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public UserPostForm authenticate(String username, String password){

        UserPostForm userPostForm = new UserPostForm();
        User user = userRepository.findByUsername(username);

        if(user == null){
            return userPostForm;
        } else if(user.getPassword().equals(password)) {
            return new UserPostForm("success",user.getRole().getRole().getPermission());
        } else {
            return new UserPostForm("failed", "");
        }
    }

    public User getUser(String username){
        return userRepository.findByUsername(username);
    }
}
