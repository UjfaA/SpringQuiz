package ujfaA.springQuiz.controller;

import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ujfaA.springQuiz.dto.QuestionDTO;
import ujfaA.springQuiz.service.QuestionService;
import ujfaA.springQuiz.service.QuizService;

@Controller
public class QuizController {
	
	@Autowired
	private QuestionService questionService;
	@Autowired
	private QuizService quizService;
	

	@GetMapping({ "/", "/home" })
	public String home() {
		return "index";
	}

	@GetMapping("/about")
	public String about() {
		return "about";
	}
	
	@GetMapping("/quiz/start")
	public String resetAndStart( Principal principal, RedirectAttributes redirectAttr) {

		if (questionService.getNumberOfQuestions() == 0) {
			redirectAttr.addFlashAttribute("errorMessage",
				"Quiz does not have any questions. Administrator needs to add at least 1 question.");
			return "redirect:/errpage";
		}
		//TODO: reset user score
		redirectAttr.addAttribute("q", 0);
		return "redirect:/quiz/show";
	}
	
	@GetMapping("/quiz/show")
	public String showQuestion(@RequestParam(name = "q") int qIndex, ModelMap model) {
		
		int numberOfQuestions = questionService.getNumberOfQuestions();
		
		if (qIndex < numberOfQuestions) {			
			
			QuestionDTO question = questionService.getQuestionByIndex(qIndex);			
			model.addAttribute("question", question);
			model.addAttribute("qIndex", qIndex);
			model.addAttribute("numberOfQuestions", numberOfQuestions);
			return "showQuestion";
		}
		else {
			return "redirect:/quiz/completed";	
			}
	}
	
	@PostMapping("/quiz/show")
	public String userAnswered( Principal principal,
								@RequestParam(name = "qId") long questionId,
								@RequestParam String selectedAnswer,
								@RequestParam(name = "q") int qIndex) {

		String username = principal.getName();
		quizService.storeUsersAnswer(username, questionId, selectedAnswer);

		return "redirect:/quiz/show?q=" + (qIndex + 1);
	}
	
	@GetMapping("/quiz/completed")
	public String onCompletion(Principal principal, ModelMap model) {
		String username = principal.getName();
		model.addAttribute("score", quizService.getUserScore(username));
		return "completion";
	}
}