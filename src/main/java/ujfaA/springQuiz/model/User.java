package ujfaA.springQuiz.model;

import java.time.LocalDateTime;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="users")
@Getter@Setter
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(unique = true, nullable = false)
	private String username;
	
	@Column(nullable = false)
	private String password;
	
	@Column(unique = true, nullable = false)
	private String email;

	@Column(nullable = false)
	private String role;
	
	@Transient
	private boolean administrator;
	
	private String firstName;
	
	private String lastName;	

	private LocalDateTime lastActive;
	
	
	// for Spring
	public boolean getAdministrator() {
		return administrator;
	}
	
	// for readability
	public boolean isAdministrator() {
		return administrator;
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
