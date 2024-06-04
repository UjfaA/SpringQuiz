package fun.quizapp.model.quiz;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class Quiz implements Cloneable {

	private long id;

	private String
		title = "Title",
		subtitle = "A fun quiz!",
		description = "";

	public String getTitleImage() {
		return "icons\\icon.png";
	}

	public String getTitleImageAlt() {
		return "Image for the quiz.";
	}

	@Override
	public Quiz clone() {
		Quiz copy;
		try {
			copy = (Quiz) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			copy = new Quiz();
		}
		return copy;
	}

}
