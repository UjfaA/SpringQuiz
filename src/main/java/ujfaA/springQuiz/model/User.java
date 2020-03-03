package ujfaA.springQuiz.model;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@NamedNativeQuery(name = "User.countCorrectAnswers", 
				query = "SELECT Count(correct_answer) "
						+ " FROM users LEFT JOIN users_answers on users.user_id = users_answers.user_id"
						+ " LEFT JOIN questions on users_answers.question_id = questions.question_id"
						+ " WHERE username = :username AND answer = correct_answer")

@Entity
@Table(name="users")
@Getter@Setter
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id")
	private Long id;
	
	@Column(unique = true, nullable = false)
	private String username;
	
	@Column(nullable = false)
	private String password;
	
	@Column(unique = true, nullable = false)
	private String email;
	
	private String firstName;
	
	private String lastName;	

	private LocalDateTime lastActive;
	
	@Column(nullable = false)
	private String role;
	
	@Transient
	private boolean isAdministrator;

	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(name = "users_answers", joinColumns = @JoinColumn(name = "user_id"))
	@MapKeyJoinColumn(name = "question_id")
	@Column(name = "answer")
	private Map<Question, String> answers = new HashMap<>();

	
	public void storeAnsweredQuestion(Question question, String answer) {
		answers.put(question, answer);
	}
	
	// for Spring
	public boolean getAdministrator() {
		return isAdministrator;
	}
	
	// for readability
	public boolean isAdministrator() {
		return isAdministrator;
	}
	
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
