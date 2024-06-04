package fun.quizapp.views.quizgallery;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.theme.lumo.LumoUtility;
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
		addClassNames(Background.CONTRAST_5, Display.FLEX, FlexDirection.COLUMN, JustifyContent.START, AlignItems.CENTER, Padding.MEDIUM, BorderRadius.LARGE);

		H3 textMain = new H3("Let AI create a fun quiz for me !");
			textMain.addClassName(LumoUtility.TextAlignment.CENTER);
		H5 text = new H5("Chose an interesting topic:");
			text.addClassName(LumoUtility.TextAlignment.CENTER);
		VerticalLayout top = new VerticalLayout(Alignment.STRETCH, textMain, text);

		HorizontalLayout bottom = new HorizontalLayout();
			bottom.addClassNames(LumoUtility.FlexWrap.WRAP, LumoUtility.Gap.SMALL);
			bottom.setWidthFull();
			bottom.add(topicButtons(aiService));
		
		this.add(top, bottom);
	}

	private Component[] topicButtons( AIService aiService) {
		var notification = new Notification(" The new quiz is created !", 4_000);
		notification.setPosition(Position.TOP_CENTER);
		notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
		return aiService
			.getTopics(5).stream()
			.map( topic -> {
				Button button = new Button(topic);
				button.addClassName(LumoUtility.FontSize.SMALL);
				button.addClickListener( click -> {
					aiService.generateQuiz(topic);
					displayQuizes();
					notification.open();
				 });
				return button;
			})
			.toArray(Component[]::new);
	}

	private void displayQuizes() {
		UI.getCurrent().access( () -> findAncestor(QuizGalleryView.class).displayQuizes());
	}

}
