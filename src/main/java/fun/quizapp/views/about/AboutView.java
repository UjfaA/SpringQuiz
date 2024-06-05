package fun.quizapp.views.about;

import fun.quizapp.views.MainLayout;
import fun.quizapp.views.quizgallery.QuizGalleryView;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import com.vaadin.flow.theme.lumo.LumoUtility.TextColor;

@PageTitle("Quiz App - About")
@Route(value = "about", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class AboutView extends VerticalLayout {

    public AboutView() {
		
		H2 title = new H2("QUIZ APP");
			title.addClassNames(Margin.Top.XLARGE, Margin.Bottom.MEDIUM);
		Paragraph subtitle = new Paragraph("A fun platform to quiz yourself and quickly create quizes 🤗");
		Paragraph github = new Paragraph("Find out about the awesome Java tech used to create the app on: ");
			github.add(new Anchor("https://github.com/UjfaA/SpringQuiz", "project's Github page"));
		H4 warn = new H4("Quiz app is in development. There are features missing and content will get lost.");
			warn.addClassName(TextColor.WARNING);
		Button button = new Button("Show me quizes!", click -> UI.getCurrent().navigate(QuizGalleryView.class));
			button.addThemeVariants(ButtonVariant.LUMO_LARGE);
			button.addClassName(Margin.Top.LARGE);
		
		add(title, subtitle, github, warn, button);
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.START);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getThemeList().add("spacing-l");
        getStyle().set("text-align", "center");
    }

}
