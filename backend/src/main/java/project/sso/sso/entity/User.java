package project.sso.sso.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;

    @JsonIgnore
    private String password;

    @ManyToOne
    @JoinTable(
            name = "join_user_role",
            inverseJoinColumns = @JoinColumn(name="role_id"),
            joinColumns = @JoinColumn(name = "user_id")

    )
    private Role role;

    @OneToOne(mappedBy = "user")
    private Profile profile;

    @ManyToMany(cascade = CascadeType.REMOVE)
    @JoinTable(
            name = "enrollment",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private Set<Course> courses = new HashSet<>();

}
