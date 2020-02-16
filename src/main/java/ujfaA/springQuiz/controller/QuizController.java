package ujfaA.springQuiz.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class QuizController {
	
	@GetMapping({ "/", "/home" })
	public String home() {
		return "index";
	}
	
	@GetMapping("/quiz")
	public String start() {
		return "quizstart";
	}
	
}