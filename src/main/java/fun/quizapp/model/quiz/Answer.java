package fun.quizapp.model.quiz;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class Answer {

	@NotBlank
	private String	text;
	private boolean correct;


	public Answer() {
		this.text = "";
	}

	public Answer( String text ) {
		this.text = text;
	}
	
	public Answer ( String text, boolean correct) {
		this.text = text;
		this.correct = correct;
	}

}
