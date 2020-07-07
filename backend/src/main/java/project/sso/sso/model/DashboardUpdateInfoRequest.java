package project.sso.sso.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DashboardUpdateInfoRequest {

    private Long courseId;
    private String info;
}
