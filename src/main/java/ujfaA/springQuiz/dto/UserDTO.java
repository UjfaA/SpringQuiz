package ujfaA.springQuiz.dto;

import java.time.LocalDateTime;
import java.util.Arrays;

import lombok.Value;
import ujfaA.springQuiz.model.Role;

@Value
public class UserDTO {

	String username;
	String email;
	String firstName;
	String lastName;
	Role role;
	LocalDateTime lastActive;
	
	
	public UserDTO(String username, String email, String firstName, String lastName, Role role, LocalDateTime lastActive) {
		
		this.username	= username;
		this.email		= redact(email);
		this.firstName	= firstName;
		this.lastName	= lastName;
		this.role 		= role;
		this.lastActive = lastActive;
	}

	private String redact(String emailFull) {
		
		StringBuilder sb = new StringBuilder();
		String[] parts = emailFull.split("@");
		char[] chars = new char[parts[0].length()];
		Arrays.fill(chars, '*');
		sb.append(chars);
		
		switch (chars.length) {
		case 0:
		case 1: break;
		case 2: {sb.setCharAt(0, parts[0].charAt(0)); break;}
		default: {
			int end = parts[0].length()-1;
			sb.setCharAt(0, parts[0].charAt(0)); 
			sb.setCharAt(end, parts[0].charAt(end));
			break;
			}
		}
		sb.append('@').append(parts[1]);
		return sb.toString();
	}

}