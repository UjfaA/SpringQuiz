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

import fun.quizapp.model.quiz.QuizService;
import fun.quizapp.views.MainLayout;


@PageTitle("Quiz Gallery")
@Route(value = "quiz-gallery", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class QuizGalleryView extends Main {

	public QuizGalleryView(QuizService service) {

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
		
		OrderedList quizContainer = new OrderedList();
		quizContainer.addClassNames(Gap.LARGE, Display.GRID, ListStyleType.NONE, Margin.NONE, Padding.NONE);

		add(horizontalLayout, quizContainer);

		var quizes = service.getAll()
			.map(QuizCardView::new)
			.collect(toCollection(ArrayList<Component>::new));
		quizContainer.add(quizes);
	}

}
