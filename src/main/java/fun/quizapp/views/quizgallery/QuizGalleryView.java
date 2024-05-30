package fun.quizapp.views.quizgallery;

import static java.util.stream.Collectors.toCollection;

import java.util.ArrayList;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.html.OrderedList;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.theme.lumo.LumoUtility.AlignItems;
import com.vaadin.flow.theme.lumo.LumoUtility.Display;
import com.vaadin.flow.theme.lumo.LumoUtility.FontSize;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import com.vaadin.flow.theme.lumo.LumoUtility.JustifyContent;
import com.vaadin.flow.theme.lumo.LumoUtility.ListStyleType;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import com.vaadin.flow.theme.lumo.LumoUtility.MaxWidth;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;

import fun.quizapp.ai.AIService;
import fun.quizapp.model.QuizService;
import fun.quizapp.views.MainLayout;


@PageTitle("Quiz Gallery")
@Route(value = "quiz-gallery", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class QuizGalleryView extends Main {

	private final QuizService service;
	private final AIService	aiService;
	private final OrderedList quizContainer = new OrderedList();

	public QuizGalleryView( QuizService service, AIService aiService ) {
		
		this.service = service;
		this.aiService = aiService;

		quizContainer.addClassNames(Gap.LARGE, Display.GRID, ListStyleType.NONE, Margin.NONE, Padding.NONE);

		addClassNames("quiz-gallery-view");
		addClassNames(MaxWidth.SCREEN_LARGE, Margin.Horizontal.AUTO, Padding.Bottom.LARGE, Padding.Horizontal.LARGE);
		
		H2 h2 = new H2("Pick an interesting quiz");
		h2.addClassNames(Margin.Bottom.XLARGE, Margin.Top.XLARGE, FontSize.XXXLARGE);
		
		Select<String> sortBy = new Select<>();
		sortBy.setLabel("Sort by");
		sortBy.setItems("Popularity", "Newest first", "Oldest first");
		sortBy.setValue("Popularity");
		
		HorizontalLayout horizontalLayout = new HorizontalLayout();
		horizontalLayout.addClassNames(AlignItems.BASELINE, JustifyContent.BETWEEN);
		horizontalLayout.add(h2, sortBy);
		
		displayQuizes();

		add(horizontalLayout, quizContainer);
	}

	void displayQuizes() {
		var quizes = service.getAll()
				.map(QuizGalleryCard::new)
				.collect(toCollection(ArrayList<Component>::new));
		quizContainer.removeAll();
		quizContainer.add(quizes);
		quizContainer.addComponentAsFirst( new AIQuizGalleryCard(aiService) );
		quizContainer.addComponentAsFirst( new NewQuizGalleryCard(service) );
	}

}
