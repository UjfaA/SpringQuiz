package fun.quizapp.views.quizgallery;

import static fun.quizapp.model.QuizService.dummyQuestions;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.theme.lumo.LumoUtility.AlignItems;
import com.vaadin.flow.theme.lumo.LumoUtility.Background;
import com.vaadin.flow.theme.lumo.LumoUtility.BorderRadius;
import com.vaadin.flow.theme.lumo.LumoUtility.Display;
import com.vaadin.flow.theme.lumo.LumoUtility.FlexDirection;
import com.vaadin.flow.theme.lumo.LumoUtility.JustifyContent;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;
import fun.quizapp.model.QuizService;
import fun.quizapp.model.quiz.Quiz;

public class NewQuizGalleryCard extends ListItem {

	NewQuizGalleryCard( QuizService service ) {

		addClassNames(Background.CONTRAST_5, Display.FLEX, FlexDirection.COLUMN, JustifyContent.CENTER , AlignItems.CENTER, Padding.MEDIUM, BorderRadius.LARGE);

		var button = new Button("Create a new fun quiz !",
								click -> {
									service.add(new Quiz(), dummyQuestions());
									displayQuizes();
								});
		this.add(button);
	}

	private void displayQuizes() {
		UI.getCurrent().access( () -> findAncestor(QuizGalleryView.class).displayQuizes());
	}

}
