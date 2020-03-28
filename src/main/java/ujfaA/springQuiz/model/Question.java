package ujfaA.springQuiz.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.Setter;


@Entity @EntityListeners(AuditingEntityListener.class)
@Table(name="questions")
@Getter @Setter
public class Question{
	
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "question_id")
	private Long id;
	
	@CreatedBy
	@ManyToOne(targetEntity = User.class, optional = false)
	@JoinColumn(name="created_by_user", referencedColumnName = "user_id")
	private User createdBy;
	
	@Column(unique = true, nullable = false)
	private String questionText;
	
	@Column(nullable = false)
	private String correctAnswer;
	
	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(name = "answers", joinColumns = @JoinColumn(name = "question_id"))
	@Column(name = "answer")
	private List<String> answers = new ArrayList<String>();	// Including the correctAnswer.

	@Transient
	private int selectedAnswerIndex;	// Used in a form when user create a question.
	
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
