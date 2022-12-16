package ujfaA.springQuiz.model.converter;

import jakarta.persistence.AttributeConverter;

import ujfaA.springQuiz.model.Role;

public class RoleConventer implements AttributeConverter<Role, String> {

	@Override
	public String convertToDatabaseColumn(Role role) {
		return role.shortName;
	}
	
	@Override
	public Role convertToEntityAttribute(String dbData) {
		return Role.fromShortName(dbData);
	}
}
