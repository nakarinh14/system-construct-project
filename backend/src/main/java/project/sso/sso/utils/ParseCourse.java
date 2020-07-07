package project.sso.sso.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.sso.sso.entity.Course;
import project.sso.sso.entity.Profile;
import project.sso.sso.model.FormattedCourse;
import project.sso.sso.repository.ProfileRepository;
import project.sso.sso.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParseCourse {

    // This model is used to only replace the InstructorId by the InstructorName to show on dashboard.
    // If anyone find more efficient way to convert through JPA, please do.

    public List<FormattedCourse> parseCourse(List<Course> courses,
                                             UserRepository userRepository,
                                             ProfileRepository profileRepository){
        List<FormattedCourse> formattedCourses = new ArrayList<>();
        for(Course course : courses){
            FormattedCourse tmp = new FormattedCourse();
            tmp.setCapacity(course.getCapacity());
            tmp.setCourseName(course.getCourseName());
            tmp.setDivision(course.getDivision());
            tmp.setCourseId(course.getCourseId());
            tmp.setDate(course.getDate());
            tmp.setId(course.getId());
            tmp.setInfo(course.getInfo());
            tmp.setRegistered(course.getRegistered());
            tmp.setTerm(course.getTerm());
            tmp.setSection(course.getSection());
            Profile profile = profileRepository.findByUser(
                    userRepository.findById(course.getInstructorId()).get()
            );
            tmp.setInstructorName(profile.getTitle() + " " + profile.getFirstname() + " " + profile.getLastname());
            formattedCourses.add(tmp);
        }
        return formattedCourses;
    }

}
