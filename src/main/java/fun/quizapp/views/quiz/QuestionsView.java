package fun.quizapp.views.quiz;

import fun.quizapp.model.QuizService;
import fun.quizapp.views.MainLayout;

import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toUnmodifiableList;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;


@PageTitle("Quiz")
@Route(value = "quiz", layout = MainLayout.class)
public class QuestionsView extends VerticalLayout implements HasUrlParameter<Long> {

	private final QuizService service;

    public QuestionsView( QuizService service ) {

		this.service = service;
		
		setMargin(true);
		setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        Button btnSubmit = new Button("Submit Answers",
				click -> Notification.show("Thank You! Answers are submitted.", 3333, Position.BOTTOM_STRETCH));
    	btnSubmit.addClickShortcut(Key.ENTER);

		add(btnSubmit);
    }

	@Override
	public void setParameter(BeforeEvent event, Long id) {
		add( service.getQuestions(id)
					.collect(mapping(QuestionCard::new, toUnmodifiableList())) );
	}

}
