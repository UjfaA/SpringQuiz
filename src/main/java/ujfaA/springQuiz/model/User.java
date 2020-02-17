package ujfaA.springQuiz.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

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
	
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(name = "QUESTIONS_ANSWERED_BY_USER",
			joinColumns = @JoinColumn(name = "USER_ID", referencedColumnName="id"),
			inverseJoinColumns = @JoinColumn(name = "QUESTION_ID", referencedColumnName = "id"))
	Set<Question> questionsAnswered = new HashSet<>();
	
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(name = "QUESTIONS_ANSWERED_CORRECTLY_BY_USER", 
			joinColumns = @JoinColumn(name = "USER_ID", referencedColumnName="id"),
			inverseJoinColumns = @JoinColumn(name = "QUESTION_ID", referencedColumnName = "id") )
	private Set<Question> questionsAnsweredCorrectly = new HashSet<Question>();
	
	
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
