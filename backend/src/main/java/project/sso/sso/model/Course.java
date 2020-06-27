package project.sso.sso.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Course {

    String courseId;
    String courseName;
    String division;
    String section;
    String instructorName;
    String capacity;
    String registered;
    String seatAvailable;
    String time;
    String info;

}
