package project.sso.sso.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.sso.sso.entity.Term;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AddNewTermRequest {
    private String semester;
    private String year;
}
