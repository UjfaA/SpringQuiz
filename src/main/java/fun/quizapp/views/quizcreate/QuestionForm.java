package fun.quizapp.views.quizcreate;

import java.util.List;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.OrderedList;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.theme.lumo.LumoUtility.ListStyleType;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;

import fun.quizapp.model.quiz.Answer;
import fun.quizapp.model.quiz.Question;

public class QuestionForm extends Div {

	private final OrderedList answersLayout = new OrderedList();
	private final Question question;
	private TextField questionField;

	QuestionForm( Question question ) {
		
		this.question = question;

		questionField = new TextField("Question text", question.getQuestionText(), "Write a question");
		questionField.setRequiredIndicatorVisible(true);
		initAnswerComponents(question.getAnswers());

		VerticalLayout layout = new VerticalLayout(Alignment.STRETCH);
		layout.setWidth("750px");
		layout.add(questionField);
		layout.add(answersLayout);
		layout.add(addAnswerButton());
		HorizontalLayout actionButtonsLayout = new HorizontalLayout( JustifyContentMode.BETWEEN, saveButton(), cancelButton());
		actionButtonsLayout.addClassName(Margin.Top.XLARGE);
		layout.add(actionButtonsLayout);
		add(layout);
	}

	private void initAnswerComponents( List<Answer> answers ) {
		answersLayout.addClassNames(ListStyleType.NONE);

		if (answers.isEmpty()) {
			answers.add( new Answer("", true) );
			answers.add( new Answer() );
			answers.add( new Answer() );
		}

		answers.forEach(this::addAnswerComponent);
	}
	
	private void addNewAnswer() {
		var answer = new Answer();
		question.getAnswers().add(answer);
		addAnswerComponent(answer);		
	}

	private void addAnswerComponent( Answer answer) {
		answersLayout.add(new AnswerComponent(answer));
	}

	private Button addAnswerButton() {
		return new Button( "Answer", VaadinIcon.PLUS_CIRCLE.create(), click -> addNewAnswer());
	}
	
	private Button saveButton() {
		Notification notification1 = new Notification("Please provide the question text.", 5000, Position.TOP_CENTER);
		Notification notification2 = new Notification("Please mark the corect answer.", 5000, Position.TOP_CENTER);
		notification1.addThemeVariants(NotificationVariant.LUMO_WARNING);
		notification2.addThemeVariants(NotificationVariant.LUMO_WARNING);
		Button saveButton = new Button("Save Question");
		saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		saveButton.addClickListener( click -> {
			if (questionField.getValue().isBlank()) {
				notification1.open();
			}
			else if ( ! questionIsValid()) {
				notification2.open();
			}
			else {
				question.setQuestionText(questionField.getValue());
				this.addQuestionToQuiz(question);
				this.backToQuiz();
				notification1.close();
				notification2.close();
			}
		});
		return saveButton;
	}
	
	private Button cancelButton() {
		Button cancelButton = new Button("Cancel");
		cancelButton.addClickListener( click -> {
			this.backToQuiz();
		});
		return cancelButton;
	}

	private boolean questionIsValid() {
		return question.getAnswers().stream().anyMatch(Answer::isCorrect);
	}
	
	private void addQuestionToQuiz(Question question) {
		findAncestor(QuizCreateView.class).addQuestion(question);;
	}
	
	private void backToQuiz() {
		findAncestor(QuizCreateView.class).showQuizView();
	}

}
