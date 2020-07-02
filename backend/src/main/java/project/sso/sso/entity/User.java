package project.sso.sso.entity;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
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

    @OneToOne
    private Role role;

    @ElementCollection
    @CollectionTable(name = "course", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "courses")
    private Set<Course> courses;


}
