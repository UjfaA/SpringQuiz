package fun.quizapp.views.quiz;

import fun.quizapp.model.QuizService;
import fun.quizapp.views.MainLayout;

import static java.util.List.copyOf;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toCollection;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;


@PageTitle("Quiz")
@Route(value = "quiz", layout = MainLayout.class)
public class QuestionsView extends VerticalLayout implements HasUrlParameter<Long> {

	private final QuizService service;
	private List<QuestionCard> questions;
	private final H1 quizName;
	private final VerticalLayout questionsDiv;

    public QuestionsView( QuizService service ) {

		this.service = service;
		
		setMargin(true);
		setDefaultHorizontalComponentAlignment(Alignment.CENTER);
		addClassName(LumoUtility.Gap.LARGE);

		quizName = new H1("Quiz");

		H3 summary = new H3();
		summary.setVisible(false);

		questionsDiv = new VerticalLayout(Alignment.START);
		var questionsOuterDiv = new HorizontalLayout(Alignment.CENTER, questionsDiv);
		
		Button btnEvaluate = new Button("Evaluate", click -> {
			int score = 0;
			for (QuestionCard q : this.questions) {
				score += q.evaluate();
			}
			summary.setText("Your score is: " + score );
			summary.setVisible(true);
		});
		
		add(quizName, questionsOuterDiv, btnEvaluate, summary);
    }

	@Override
	public void setParameter(BeforeEvent event, Long id) {
		questions = service.getQuestions(id)
					.collect( mapping( QuestionCard::new, toCollection(ArrayList<QuestionCard>::new) ));
		questionsDiv.add( copyOf(questions) );

		service.getById(id).ifPresent(
				quiz -> quizName.setText(quiz.getTitle()));
	}

}
