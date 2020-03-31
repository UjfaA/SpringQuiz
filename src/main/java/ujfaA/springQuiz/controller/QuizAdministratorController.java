package ujfaA.springQuiz.controller;


import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ujfaA.springQuiz.dto.QuestionDTO;
import ujfaA.springQuiz.model.Question;
import ujfaA.springQuiz.service.QuestionService;
import ujfaA.springQuiz.service.QuizService;
import ujfaA.springQuiz.service.UserService;

@Controller
public class QuizAdministratorController {
		
	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private QuizService quizService;
	
	@GetMapping("/questions")
	public String getQuestions(@RequestParam(name = "byMe", defaultValue = "false") boolean createdByCurrentUser,
								Principal principal, ModelMap model) {
		
		if (createdByCurrentUser) {
			String currentUsername = principal.getName();
			model.addAttribute("questions", questionService.listAllByUser(currentUsername));			
		}
		else {
			model.addAttribute("questions", questionService.listAll());			
		}
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
	public String addQuestion(@Valid Question question, BindingResult bindingResult, ModelMap model, RedirectAttributes redirectAttrs) {
		
		if (bindingResult.hasErrors()) {
			model.addAttribute("numberOfAnswers", question.getAnswers().size());
			model.addAttribute("MAX_ANSWERS", 5);
			return "newQuestion";
		}
/*		
		boolean hasDuplicateAnswers = questionService.containsRepeatedAnswers(question);
		if (hasDuplicateAnswers) {
			model.addAttribute("numberOfAnswers", question.getAnswers().size());			
			model.addAttribute("MAX_ANSWERS", 5);
			model.addAttribute("message", "Each answer has to be different.");
			return "newQuestion";
		}
*/		
		try {
			questionService.save(question);			
		} catch (Exception e) {
			redirectAttrs.addAttribute("numberOfAnswers", question.getAnswers().size());			
			redirectAttrs.addFlashAttribute("message", "There was an error while adding the question.");
			redirectAttrs.addFlashAttribute(question);
			return"redirect:/questions/new";
		}
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

		model.addAttribute("texts", questionService.GetQuestionTexts());
		model.addAttribute("selectedIndex", qIndex);
		model.addAttribute("checked", correctly);
		
		if (qIndex == -1) {
			model.addAttribute("usernames", userService.getUsernamesThatAnsweredEveryQ(correctly));
		}
		else {
			QuestionDTO q = questionService.getQuestionByIndex(qIndex);
			model.addAttribute("usernames", userService.getUsernamesThatAnswered(q, correctly));
		}
		return"usersEngagement";
	}	
}
