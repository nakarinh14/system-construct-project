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
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String courseId;  // ICCS101, etc.

    @NotNull
    private int section; // Section 1,2,3..

    @NotNull
    private String date;

    @NotNull
    private int capacity;

    private int registered;

    @ManyToOne
    @JoinTable(
            name = "join_instructor_course",
            inverseJoinColumns = @JoinColumn(name="instructor_id"),
            joinColumns = @JoinColumn(name = "course_id")

    )
    private User instructorId;

    private String info;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "join_enrollment",
            inverseJoinColumns = @JoinColumn(name="user_id"),
            joinColumns = @JoinColumn(name = "course_id")
    )
    private Set<User> students = new HashSet<>();
}
