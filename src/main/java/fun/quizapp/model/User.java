package fun.quizapp.model;

import java.time.LocalDateTime;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;


@Getter@Setter
public class User {

	private Long id;
	
	@NotBlank
	private String username;
	
	private String password;
	
	@NotBlank
	@Email
	private String email;
	
	private String firstName;
	
	private String lastName;	

	private LocalDateTime lastActive;

	@Override
	public String toString() {
		return username;
	}
	
	@Override
	public boolean equals(Object other) {
		if ( ! (other instanceof User))
			return false;
		User otherU = (User) other;
		return this.username.equals(otherU.username);
	}
	
	@Override
	public int hashCode() {
		return this.username.hashCode();
	}
}
