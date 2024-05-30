package fun.quizapp.views.quizgallery;


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

import fun.quizapp.ai.AIService;

public class AIQuizGalleryCard extends ListItem {

	AIQuizGalleryCard( AIService aiService ) {
		addClassNames(Background.CONTRAST_5, Display.FLEX, FlexDirection.COLUMN, JustifyContent.CENTER , AlignItems.CENTER, Padding.MEDIUM, BorderRadius.LARGE);

		var button = new Button("Let AI create a fun quiz for me !",
								click -> {
									aiService.generateQuiz("World geography");
									displayQuizes();
								});
		this.add(button);
	}

	private void displayQuizes() {
		UI.getCurrent().access( () -> findAncestor(QuizGalleryView.class).displayQuizes());
	}

}
