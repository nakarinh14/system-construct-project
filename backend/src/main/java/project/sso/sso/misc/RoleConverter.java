package project.sso.sso.misc;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class RoleConverter implements AttributeConverter<RoleType, String> {
    // Update Enum approach. Method from https://www.baeldung.com/jpa-persisting-enums-in-jpa
    @Override
    public String convertToDatabaseColumn(RoleType role) {
        if (role == null) {
            return null;
        }
        return role.getPermission();
    }

    @Override
    public RoleType convertToEntityAttribute(String code) {
        if (code == null) {
            return null;
        }

        return Stream.of(RoleType.values())
                .filter(c -> c.getPermission().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}