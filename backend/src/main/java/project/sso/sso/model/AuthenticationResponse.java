package project.sso.sso.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AuthenticationResponse {

    private String status;
    private String username;
    private String firstname;
    private String lastname;
}
