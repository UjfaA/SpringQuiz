package fun.quizapp.views.quizmanagement;

import static java.util.stream.Collectors.toCollection;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.details.DetailsVariant;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;

import fun.quizapp.model.QuizService;
import fun.quizapp.model.quiz.Question;
import fun.quizapp.model.quiz.Quiz;
import fun.quizapp.views.MainLayout;

@Route(layout = MainLayout.class)
@PageTitle("Quiz Management")
public class QuizMangementView extends Main {

	private QuizService service;
	private VerticalLayout layout = new VerticalLayout ();

	public QuizMangementView( QuizService service ) {

		addClassName(LumoUtility.Padding.Top.LARGE);
		
		this.service = service;
		layout.setAlignItems(Alignment.CENTER);
		layout.setJustifyContentMode(JustifyContentMode.CENTER);

		layout.add(quizCards());
		this.add(layout);
	}

	private List<Component> quizCards() {
		return service
				.getAll()
				.map(this::toQuizActions)
				.collect(toCollection(ArrayList::new));
	}

	private HorizontalLayout toQuizActions( Quiz quiz ) {

		Details details = new Details();
		details.addThemeVariants(DetailsVariant.FILLED);
		var questions = service.getQuestions(quiz.getId()).map(Question::toString).toList();
		var radioButtonGroup  = new RadioButtonGroup<>("", questions);
		radioButtonGroup.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
		details.setSummaryText(quiz.getTitle());
		details.add(radioButtonGroup);

		Button delete = new Button("Delete",
				click -> {
					service.remove(quiz);
					displayQuizes();
				});
		delete.addThemeVariants(ButtonVariant.LUMO_ERROR);

		HorizontalLayout quizCard = new HorizontalLayout( Alignment.CENTER , details, delete);
		quizCard.addClassNames(LumoUtility.Border.ALL, LumoUtility.BorderRadius.SMALL, LumoUtility.Padding.Start.MEDIUM, LumoUtility.Padding.End.MEDIUM);
		return quizCard;
	}

	private void displayQuizes() {
		UI.getCurrent().access( () -> {
			layout.removeAll();
			layout.add(quizCards());
		});
	}

}