package fun.quizapp.views.quizcreate;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.OrderedList;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.theme.lumo.LumoUtility.TextColor;

import fun.quizapp.model.quiz.Answer;

public class AnswerComponent extends ListItem {

	private Checkbox isCorrectCheckBox = new Checkbox();
	private TextField text = new TextField();
	private Registration registration;
	private Binder<Answer> binder = new Binder<>(Answer.class);

	AnswerComponent( Answer answer ) {

		text.setPlaceholder("Write an answer");
		text.setRequiredIndicatorVisible(true);
		text.setWidthFull();
		if (answer.isCorrect())
			text.addClassName(TextColor.SUCCESS);

		binder.bind(text, Answer::getText, Answer::setText);
		binder.bind(isCorrectCheckBox, "correct");
		binder.setBean(answer);

		add(new HorizontalLayout(isCorrectCheckBox, text));
	}

	@Override
	protected void onAttach(AttachEvent attachEvent) {
		super.onAttach(attachEvent);
		isCorrectCheckBox.addValueChangeListener( event -> {
			if (event.getValue())
				ComponentUtil.fireEvent( findAncestor(OrderedList.class), new AnswerMarkedEvent( isCorrectCheckBox, false));
		});        
		registration = ComponentUtil.addListener( findAncestor(OrderedList.class), AnswerMarkedEvent.class, event -> {
			if (event.getSource() == isCorrectCheckBox) {
				text.addClassName(TextColor.SUCCESS);
			} else {
				isCorrectCheckBox.setValue(false);
				text.removeClassName(TextColor.SUCCESS);
			}
		});
	}

	@Override
	protected void onDetach(DetachEvent detachEvent) {
		super.onDetach(detachEvent);
		registration.remove();
	}
}

class AnswerMarkedEvent extends ComponentEvent<Checkbox> {

	public AnswerMarkedEvent(Checkbox isCorrectCheckBox, boolean fromClient) {
		super(isCorrectCheckBox, fromClient);
	}
}

