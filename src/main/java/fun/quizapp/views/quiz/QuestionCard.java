package fun.quizapp.views.quiz;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.data.renderer.ComponentRenderer;

import fun.quizapp.model.quiz.Answer;
import fun.quizapp.model.quiz.Question;

class QuestionCard extends Div {

	QuestionCard(Question question) {

		var answers = new RadioButtonGroup<Answer>(question.getQuestionText(), question.getAnswers());
		answers.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
		answers.setRenderer( new ComponentRenderer<>( ans -> new Div(ans.getText()) ));

		add(answers);
	}
}
