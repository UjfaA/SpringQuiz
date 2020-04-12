package ujfaA.springQuiz.model.converter;

import javax.persistence.AttributeConverter;

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
