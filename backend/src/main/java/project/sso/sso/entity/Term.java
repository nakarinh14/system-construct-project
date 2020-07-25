package project.sso.sso.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.sso.sso.model.AddNewTermRequest;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Term {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String semester;
    private String year;

    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinTable(
            name="join_course_term",
            joinColumns = @JoinColumn(name="term_id"),
            inverseJoinColumns=@JoinColumn(name="course_id")
    )
    @JsonIgnore
    private Set<Course> courses = new HashSet<>();

    public Term(AddNewTermRequest request) {
        this.id = request.getId();
        this.semester = request.getSemester();
        this.year = request.getYear();
    }
}
