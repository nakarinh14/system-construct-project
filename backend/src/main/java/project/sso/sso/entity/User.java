package project.sso.sso.entity;

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
    @NotNull
    private Long id;

    @NotNull
    private String username;

    @NotNull
    private String password;

    @OneToOne(mappedBy = "user")
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
