package fun.quizapp.model.quiz;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
//import org.hibernate.validator.constraints.UniqueElements;

//import fun.quizapp.model.User;
import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class Question{
	
	private long id;
	
//	private User createdBy;
	
	/* validation */
	@NotBlank
	@JsonAlias({"question"})
	private String questionText;
	
	/* validation */
	@NotEmpty()
//	@UniqueElements(message = "Each answer has to be different.")
	private List<Answer> answers = new ArrayList<>();
	
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
		return questionText.hashCode();
	}

}
