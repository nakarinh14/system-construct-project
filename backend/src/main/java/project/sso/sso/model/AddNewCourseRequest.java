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
public class AddNewCourseRequest {

    private String courseId;  // ICCS101, etc.
    private String courseName;
    private String division;
    private int section; // Section 1,2,3..
    private String date; //test
    private int capacity;
    private int registered;
    private Long instructorId;
    private Term term;
    private String info;
}
