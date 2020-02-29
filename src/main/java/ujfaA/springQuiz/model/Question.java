package ujfaA.springQuiz.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name="QUESTIONS")
@Getter @Setter
public class Question{
	
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(unique = true, nullable = false)
	private String questionText;
	
	@Column(nullable = false)
	private String correctAnswer;
	
	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(name = "question_answers")
	@Column(name = "answer")
	private List<String> answers = new ArrayList<String>();

	@Transient
	private int selectedAnswerIndex;
	
	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(name = "users_answered", joinColumns = @JoinColumn(name = "Question_id"))
	@MapKeyJoinColumn(name = "user_id")
	@Column(name = "chosen_answer")
	private Map<User,String> usersAnswered = new HashMap<User, String>();
	
	
	public Question() {
	}
	
	@Override
	public String toString() {
		return questionText;
	}
	
	@Override
	public boolean equals(Object other) {
		if ( ! (other instanceof Question))
			return false;
		Question otherQ = (Question) other;
		return this.questionText.equals(otherQ.questionText);
	}
	
	@Override
	public int hashCode() {
		return this.questionText.hashCode();
	}
}
