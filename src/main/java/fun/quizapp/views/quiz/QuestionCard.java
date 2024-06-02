package fun.quizapp.views.quiz;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.data.renderer.ComponentRenderer;

import fun.quizapp.model.quiz.Answer;
import fun.quizapp.model.quiz.Question;


class QuestionCard extends Div {

	private final RadioButtonGroup<Answer> answers;
	private final int points;

	QuestionCard(Question question) {

		addClassName("question-card");

		points = question.getPoints();

		answers = new RadioButtonGroup<Answer>(question.getQuestionText(), question.getAnswers());
		answers.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
		answers.setRenderer( new ComponentRenderer<>( ans -> new Div(ans.getText()) ));

		add(answers);
	}

	int evaluate() {
		var answer	= answers.getOptionalValue();
		answer.ifPresent(this::displayOutcome);
		if (answer.isPresent() && answer.get().isCorrect())
			return points;
		else
			return 0;
	}
	
	private void displayOutcome( Answer answer ) {
		if (answer.isCorrect()) {
			answers.setHelperText("Correct");
			answers.setClassName("primary");
		}
		else {
			answers.setHelperText("Oops, that is not correct");
			answers.setClassName("error");
		}
		answers.setReadOnly(true);
	}

}
