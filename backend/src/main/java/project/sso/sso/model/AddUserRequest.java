package project.sso.sso.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AddUserRequest {
    private String username;
    private String password;
    private Long role;
    private String firstname;
    private String lastname;
    private String title;
}
