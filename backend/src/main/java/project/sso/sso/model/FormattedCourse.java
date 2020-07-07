package project.sso.sso.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.sso.sso.entity.Term;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FormattedCourse {

    private Long id;
    private String courseId;  // ICCS101, etc.
    private int section; // Section 1,2,3..
    private String date;
    private String division;
    private String courseName;
    private int capacity;
    private int registered;
    private String instructorName;
    private Term term;
    private String info;
}
