package ujfaA.springQuiz.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ujfaA.springQuiz.model.Question;
import ujfaA.springQuiz.service.QuestionService;
import ujfaA.springQuiz.service.QuizService;
import ujfaA.springQuiz.service.UserService;

@Controller
public class QuizAdministratorController {
		
	@Autowired
	QuestionService questionService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	QuizService quizService;
	
	@GetMapping("/questions")
	public String getQuestions(ModelMap model) {
		model.addAttribute("questions", questionService.listAll());
		return "questions";
	}
	
	@GetMapping("/questions/new")
	public String newQuestion(@ModelAttribute Question question,
							@RequestParam(name = "numberOfAnswers", defaultValue = "3") int numberOfAnswers,
							ModelMap model) {
		
		model.addAttribute("numberOfAnswers", numberOfAnswers);
		model.addAttribute("MAX_ANSWERS", 5);
		return "newQuestion";
	}
	
	@PostMapping("/questions/new")
	public String addQuestion(@ModelAttribute Question question,
								@RequestParam int numberOfAnswers,
								RedirectAttributes redirectAttrs) {
		
		boolean hasDuplicateAnswers = questionService.containsRepeatedAnswers(question);
		if (hasDuplicateAnswers) {
			redirectAttrs.addAttribute("numberOfAnswers", question.getAnswers().size());			
			redirectAttrs.addFlashAttribute("message", "Each answer has to be different.");
			redirectAttrs.addFlashAttribute(question);
			return"redirect:/questions/new";
		}
		questionService.save(question);
		return "redirect:/questions";
	}
	
	@PostMapping("/questions/delete")
	public String removeQuestion(@RequestParam Long id) {
		quizService.removeQuestion(id);
		return "redirect:/questions";
	}
	
	@GetMapping("/users")
	public String getUsersInfo(ModelMap model) {
		model.addAttribute("users", userService.getUsersInfo());
		return "usersInfo";
	}
	
	@GetMapping("/users/usersEng")
	public String getUserEngagement(@RequestParam(name = "q", defaultValue = "-1") int qIndex,
									@RequestParam(name = "correctly", defaultValue = "false") boolean correctly,
									ModelMap model) {

		model.addAttribute("texts", questionService.GetQuestionsText());
		model.addAttribute("selectedIndex", qIndex);
		model.addAttribute("checked", correctly);
		
		if (qIndex == -1) {
			model.addAttribute("usernames", userService.getUsernamesThatAnsweredEveryQ(correctly));
		}
		else {
			Question q = questionService.getQuestionByIndex(qIndex);
			model.addAttribute("usernames", userService.getUsernamesThatAnswered(q, correctly));
		}
		return"usersEngagement";
	}
	
}
