package ujfaA.springQuiz.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ujfaA.springQuiz.service.QuestionService;
import ujfaA.springQuiz.service.QuizService;

@Controller
public class QuizController {
	
	@Autowired
	QuestionService questionService;
	@Autowired
	QuizService quizService;
	
	
	@GetMapping({ "/", "/home" })
	public String home() {
		return "index";
	}
	
	@GetMapping("/start")
	public String resetAndStart( Principal principal, RedirectAttributes redirectAttr) {

		if (questionService.getNumberOfQuestions() == 0) {
			redirectAttr.addFlashAttribute("errorMessage",
				"Quiz does not have any questions. Administrator needs to add at least 1 question.");
			return "redirect:/errpage";
		}
		
//		quizService.resetScore(principal.getName());
		redirectAttr.addAttribute("qIndex", 0);
		return "redirect:/showQuestion";
	}
	
}