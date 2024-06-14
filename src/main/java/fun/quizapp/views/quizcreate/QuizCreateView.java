package fun.quizapp.views.quizcreate;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.details.DetailsVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.html.OrderedList;
import com.vaadin.flow.component.html.OrderedList.NumberingType;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.AlignItems;
import com.vaadin.flow.theme.lumo.LumoUtility.AlignSelf;
import com.vaadin.flow.theme.lumo.LumoUtility.Display;
import com.vaadin.flow.theme.lumo.LumoUtility.FlexDirection;
import com.vaadin.flow.theme.lumo.LumoUtility.FontSize;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import com.vaadin.flow.theme.lumo.LumoUtility.TextColor;

import fun.quizapp.model.QuizService;
import fun.quizapp.model.quiz.Answer;
import fun.quizapp.model.quiz.Question;
import fun.quizapp.model.quiz.Quiz;
import fun.quizapp.views.MainLayout;
import fun.quizapp.views.quizgallery.QuizGalleryView;

@PageTitle("Quiz App - Create Quiz")
@Route(value = "quiz/create", layout = MainLayout.class)
public class QuizCreateView extends Main {

	private final TextField title;
	private final TextField subtitle;
	private final VerticalLayout questionsLayout;
	private List<Component> quizViewComponents;
	private final List<Question> questions = new ArrayList<>();
	private final QuizService service;

	public QuizCreateView( QuizService service) {
		
		this.service = service;

		addClassNames(Display.FLEX, FlexDirection.COLUMN, AlignItems.CENTER, Margin.Top.LARGE);

		H2 h2 = new H2("Create a new Quiz");
		h2.addClassName(AlignSelf.START);
		
		title = new TextField("Quiz Title");
		title.addClassNames(FontSize.LARGE, Margin.Bottom.SMALL);
		subtitle = new TextField("Quiz Subtitle");
		subtitle.addClassNames(Margin.Bottom.MEDIUM);			

		questionsLayout = new VerticalLayout(Alignment.CENTER);

		Button addQuestionButton = new Button("Add Question", VaadinIcon.PLUS_CIRCLE.create(), click -> {
				quizViewComponents = this.getChildren().toList();
				this.removeAll();
				this.add( new QuestionForm(new Question()) );
		});
		addQuestionButton.addClassNames(Margin.Top.LARGE);

		HorizontalLayout actionButtonsLayout = new HorizontalLayout( JustifyContentMode.BETWEEN, saveQuizButton(), cancelQuizButton());
		actionButtonsLayout.addClassName(Margin.Top.XLARGE);
		
		VerticalLayout layout = new VerticalLayout(Alignment.STRETCH, h2, title, subtitle, questionsLayout, addQuestionButton, actionButtonsLayout);
		layout.setWidth("750px");
		add(layout);
	}

	void showQuizView() {
		this.removeAll();
		this.add(quizViewComponents);
	}

	void addQuestion( Question question) {
		questions.addLast(question);
		questionsLayout.add( asDetailsComponent(question) );
	}
	
	private Details asDetailsComponent( Question question) {
		var answersList = new OrderedList( answersAsListItems(question.getAnswers()) );
			answersList.setType(NumberingType.LOWERCASE_LETTER);
		var details = new Details( question.getQuestionText(), answersList);
			details.addThemeVariants(DetailsVariant.FILLED);
		return details;
	}

	private ListItem[] answersAsListItems( List<Answer> answers) {
		return answers.stream()
				.map(this::answerToListItem)
				.toArray(ListItem[]::new);
	}

	private ListItem answerToListItem( Answer answer ) {
		 var item = new ListItem(answer.getText());
		 if (answer.isCorrect())
		 	item.addClassName(TextColor.SUCCESS);
		 return item;
	}

	private Button saveQuizButton() {
		var saveButton = new Button("Save Quiz");
		saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		saveButton.addClickListener( click -> {
			if (questions.isEmpty()) {
				Notification.show("Add at least one question to the quiz.", 5000, Position.TOP_CENTER);
			}
			else {
				String title = this.title.getValue();
				String subTitle = this.subtitle.getValue();
				if (title.isBlank())
					title = "No Title Quiz"; 
				Quiz quiz = new Quiz();
				quiz.setTitle(title);
				quiz.setSubtitle(subTitle);
				service.add( quiz, questions );
				Notification notification = new Notification("Quiz " + title + " created successfully.", 5000, Position.TOP_CENTER);
				notification.open();
				UI.getCurrent().navigate(QuizGalleryView.class);
			}
		});
		return saveButton;
	}

	private Button cancelQuizButton() {
		var cancelButton = new Button("Cancel");
		cancelButton.addClickListener( click -> UI.getCurrent().navigate(QuizGalleryView.class));
		return cancelButton;
	}

}
