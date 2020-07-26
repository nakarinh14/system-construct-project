package project.sso.sso.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import project.sso.sso.model.AddNewCourseRequest;

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

    private String courseId;  // ICCS101, etc.

    private String courseName;

    private String division;

    private int section; // Section 1,2,3..

    private String date; //test

    private int capacity;

    private int registered;

    private Long instructorId;

    @ManyToOne
    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    @JoinTable(
            name = "join_term_course",
            inverseJoinColumns = @JoinColumn(name = "term_id"),
            joinColumns = @JoinColumn(name = "course_id")
    )


    private Term term;

    private String info;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "join_enrollment",
            inverseJoinColumns = @JoinColumn(name = "user_id"),
            joinColumns = @JoinColumn(name = "course_id")
    )
    private Set<User> students = new HashSet<>();

    public Course(AddNewCourseRequest request) {
        this.setCourseId(request.getCourseId());
        this.setCourseName(request.getCourseName());
        this.setDivision(request.getDivision());
        this.setSection(request.getSection());
        this.setDate(request.getDate());
        this.setCapacity(request.getCapacity());
        this.setRegistered(request.getRegistered());
        this.setInstructorId(request.getInstructorId());
    }
}
