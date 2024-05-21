package fun.quizapp.model.quiz;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.serializer.reference.Lazy;

import fun.quizapp.model.Question;

import lombok.Getter;
import lombok.Setter;

import static lombok.AccessLevel.NONE;


@Getter @Setter
public class Quiz {

	private String 
		title = "Title",
		subtitle = "A fun quiz!",
		description ="Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut.";

	@Getter(NONE) @Setter(NONE)
	private Lazy<List<Question>>
		questions = Lazy.Reference(new ArrayList<>());

	public String getTitleImage() {
		return "icons\\icon.png";
	}

	public String getTitleImageAlt() {
		return "Image for the quiz.";
	}

	public List<Question> getQuestions() {
		return questions.get();
	}

}
