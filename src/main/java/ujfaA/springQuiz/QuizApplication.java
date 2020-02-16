package ujfaA.springQuiz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class QuizApplication extends SpringBootServletInitializer {
	
	@Override
    protected SpringApplicationBuilder configure (SpringApplicationBuilder builder) {
        return builder.sources(QuizApplication.class);
    }

	public static void main(String[] args) {
		SpringApplication.run(QuizApplication.class, args);
	}

}
