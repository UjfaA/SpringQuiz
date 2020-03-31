package ujfaA.springQuiz.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.UniqueElements;
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
	private long id;
	
	@CreatedBy
	@ManyToOne(targetEntity = User.class, optional = false)
	@JoinColumn(name="created_by_user", referencedColumnName = "user_id", nullable = false)
	private User createdBy;
	
	/* validation */
	@NotBlank
	//
	@Column(unique = true, nullable = false)
	private String questionText;
	
	@Column(nullable = false)
	private String correctAnswer;
	
	/* validation */
	@NotEmpty()
	@UniqueElements(message = "Each answer has to be different.")
	//
	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(name = "answers", joinColumns = @JoinColumn(name = "question_id", referencedColumnName = "question_id"))
	@OrderColumn(name = "ordinal", columnDefinition = "tinyint") 
	@Column(name = "answer", nullable = false)
	private List<@NotBlank String> answers = new ArrayList<String>();	// Including the correctAnswer.

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
