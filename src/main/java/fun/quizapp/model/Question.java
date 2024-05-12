package fun.quizapp.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.UniqueElements;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class Question{
	
	private long id;
	
	private User createdBy;
	
	/* validation */
	@NotBlank
	private String questionText;
	
	private String correctAnswer;
	
	/* validation */
	@NotEmpty()
	@UniqueElements(message = "Each answer has to be different.")
	private List<@NotBlank String> answers = new ArrayList<String>();	// Includes the correctAnswer.


	@Getter(value = AccessLevel.NONE)
	@Setter(value = AccessLevel.NONE)
	private int hash; // Default to 0

	@Getter(value = AccessLevel.NONE)
	@Setter(value = AccessLevel.NONE)
	private boolean hashIsZero; // Default to false;
	
	
	public Question() {}
	
	@Override
	public String toString() {
		return questionText;
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if (this == obj)
			return true;
		if ( ! (obj instanceof Question))
			return false;
		Question otherQ = (Question) obj;
		if ( ! this.questionText.equals(otherQ.questionText))
			return false;
		/* Check if answers are the same - irrelevant of order. */
		return new HashSet<>(this.answers).equals(new HashSet<>(otherQ.answers));
	}

	@Override
	public int hashCode() {
		
		int h = hash;
		if (h == 0 && !hashIsZero) {
			h = questionText.hashCode();
			for (String ans : answers) {
				h += ans.hashCode();
			}
			if (h == 0) {
				hashIsZero = true;
			} else {
				hash = h;
			}
		}
		return h;
	}

}
