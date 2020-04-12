package ujfaA.springQuiz.controller;


import java.security.Principal;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
	public String getQuestions(ModelMap model) {		
		model.addAttribute("questions", questionService.listAll());
		return "questions";
	}
	
	@GetMapping("/questions/byMe")
	public String getQuestionsByUser(Principal principal, ModelMap model) {
		String currentUsername = principal.getName();
		model.addAttribute("questions", questionService.listAllByUser(currentUsername));
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
		if (questionService.exist(question)) {
			redirectAttrs.addAttribute("numberOfAnswers", question.getAnswers().size());			
			redirectAttrs.addFlashAttribute("message", "A question like this already exist.");
			redirectAttrs.addFlashAttribute(question);
			return"redirect:/questions/new";
		}
		try {
			questionService.save(question);		
		} catch (Exception e) {
			redirectAttrs.addAttribute("numberOfAnswers", question.getAnswers().size());			
			redirectAttrs.addFlashAttribute("message", "There was an error while adding the question.");
			redirectAttrs.addFlashAttribute(question);
			return"redirect:/questions/new";
		}
		return "redirect:/questions/byMe";
	}

	@PostMapping("/questions/delete")
	public String removeQuestion(@RequestParam Long id, @RequestParam String createdBy,
							Authentication auth, RedirectAttributes redirectAttrs) {
		
		String message;

		if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_CONTRIBUTOR")) ) {
			
			if (auth.getName().equals(createdBy)) {
				try {
					quizService.removeQuestion(id);
					message = "The question has been removed.";
				} catch (Exception e) {
					message = "There had been an error while trying to remove the question.";
				}
			}
			else {
				message ="You don't have the required permission to delete this question.";
			}
			redirectAttrs.addFlashAttribute("message", message);
			return "redirect:/questions/byMe";				
		}
		else {
			try {
				quizService.removeQuestion(id);
				message = "The question has been removed.";
			} catch (Exception e) {
				message = "There had been an error while trying to remove the question.";
			}
			redirectAttrs.addFlashAttribute("message", message);
			return "redirect:/questions";
		}
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
