package project.sso.sso.entity;

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
    private Long id;
    private String username;
    private String password;

    @OneToOne
    private Role role;

    @ElementCollection
    @CollectionTable(name = "course", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "courses")
    private Set<Course> courses;


}
