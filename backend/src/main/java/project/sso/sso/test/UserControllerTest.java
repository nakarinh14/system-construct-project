package project.sso.sso.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import project.sso.sso.entity.Course;
import project.sso.sso.entity.User;
import project.sso.sso.model.UserPostForm;
import project.sso.sso.repository.CourseRepository;
import project.sso.sso.repository.UserRepository;
import project.sso.sso.service.CourseService;
import project.sso.sso.service.UserService;

import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


@RestController
public class UserControllerTest {

    @Autowired
    private UserService userService;


    @PostMapping("/api/test/student/add")
    public User addUser(@RequestParam String username,
                        @RequestParam String password,
                        @RequestParam String firstname,
                        @RequestParam String lastname){

        return userService.addUser(username, password, firstname, lastname);
    }

    @PostMapping("/api/test/student/get")
    public User getUser(@RequestParam String username){
        return userService.getUser(username);
    }

    @PostMapping("/api/test/auth")
    public User authenticate(@RequestBody Map<String, Object> payload, HttpSession session){
        String username = (String) payload.get("username");
        String password = (String) payload.get("password");

        User user = userService.authenticate(username,password);
        if(user != null){
            session.setAttribute("role", user.getRole().getRole().getPermission());
            session.setAttribute("username", username);
        }
        return user;
    }

    @PostMapping("/api/test/session")
    public void authenticate( HttpSession session){
        System.out.println((String) session.getAttribute("username"));
    }
}
