package project.sso.sso.misc;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleType {
    // Update Enum approach. Method from https://www.baeldung.com/jpa-persisting-enums-in-jpa
    ADMIN("admin"),INSTRUCTOR("instructor"),STUDENT("student");

    private final String permission;

}
