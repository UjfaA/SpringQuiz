package fun.quizapp.views.quizgallery;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.theme.lumo.LumoUtility.AlignItems;
import com.vaadin.flow.theme.lumo.LumoUtility.AlignSelf;
import com.vaadin.flow.theme.lumo.LumoUtility.Background;
import com.vaadin.flow.theme.lumo.LumoUtility.BorderRadius;
import com.vaadin.flow.theme.lumo.LumoUtility.Display;
import com.vaadin.flow.theme.lumo.LumoUtility.FlexDirection;
import com.vaadin.flow.theme.lumo.LumoUtility.FontSize;
import com.vaadin.flow.theme.lumo.LumoUtility.FontWeight;
import com.vaadin.flow.theme.lumo.LumoUtility.JustifyContent;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import com.vaadin.flow.theme.lumo.LumoUtility.Overflow;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;
import com.vaadin.flow.theme.lumo.LumoUtility.TextColor;

import fun.quizapp.model.quiz.Quiz;
import fun.quizapp.views.quiz.QuestionsView;

class QuizGalleryCard extends ListItem {

	private final long qID;

	QuizGalleryCard(Quiz quiz) {

		qID = quiz.getId();

		addClassNames(Background.CONTRAST_5, Display.FLEX, FlexDirection.COLUMN, AlignItems.START, Padding.MEDIUM, BorderRadius.LARGE);

		Image image = new Image();
		image.setWidth( "100%");
		image.setHeight("100%");
		image.setSrc(quiz.getTitleImage());
		image.setAlt(quiz.getTitleImageAlt());

		Div imageContainer = new Div();
		imageContainer.addClassNames(Background.CONTRAST, Display.FLEX, AlignItems.CENTER, JustifyContent.CENTER, AlignSelf.CENTER,
				Margin.Bottom.MEDIUM, Overflow.HIDDEN, BorderRadius.MEDIUM);
		imageContainer.setHeight("100px");
		imageContainer.setWidth( "100%");
		imageContainer.add(image);

		Span title = new Span();
		title.addClassNames(FontSize.XLARGE, FontWeight.SEMIBOLD);
		title.setText(quiz.getTitle());

		Span subtitle = new Span();
		subtitle.addClassNames(FontSize.SMALL, TextColor.SECONDARY);
		subtitle.setText(quiz.getSubtitle());

		Paragraph description = new Paragraph();
		description.addClassName(Margin.Vertical.MEDIUM);
		description.add(quiz.getDescription());

		add(imageContainer, title, subtitle, description);

		addClickListener( click -> this.getUI().ifPresent( ui ->
			ui.navigate(QuestionsView.class, qID)));
	}

	long qID() {return qID;}
}
