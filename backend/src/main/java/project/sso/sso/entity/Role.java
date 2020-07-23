package project.sso.sso.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.sso.sso.misc.RoleType;

import javax.persistence.*;
import java.util.Set;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private RoleType role;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinTable(
            name="set_role_user",
            joinColumns = @JoinColumn(name="role_id"),
            inverseJoinColumns=@JoinColumn(name="user_id")
    )
    private Set<User> user;

}
