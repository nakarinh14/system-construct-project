package project.sso.sso.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.sso.sso.entity.Term;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class DashboardResponse {
    private String role;
    private List<FormattedCourse> courses;
    private List<Term> termOptions;
    private Term requestedTerm;
}
