package ujfaA.springQuiz.controller;


import java.security.Principal;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
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
	public String newQuestion(
			@ModelAttribute Question question,
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
	
	@GetMapping("/questions/{qId:[0-9]+}")
	public String getQuestionStats(@PathVariable("qId") long qId, Authentication auth, RedirectAttributes redirectAttrs, ModelMap model) {

		QuestionDTO question = questionService.getQuestion(qId);
		if (question == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Question not found.");
		
		if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_CONTRIBUTOR")) ) {
			
			String author = question.getCreatedBy().getUsername();			
			if ( ! auth.getName().equals(author)) {
				redirectAttrs.addFlashAttribute("message","You don't have the required permission to see the question's stats.");
				return "redirect:/questions/byMe";
			}
		}
		model.addAttribute("question", question);
		model.addAttribute("answeredPercentage", userService.getAnsweredPercentage(qId));
		model.addAttribute("answersDistribution", userService.getAnswersDistribution(qId));
		return "questionStats";
	}

	@PostMapping("/questions/delete")
	public String removeQuestion(@RequestParam long qId, Authentication auth, RedirectAttributes redirectAttrs) {
		
		String redirectModifier = "";

		if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_CONTRIBUTOR")) ) {

			QuestionDTO q = questionService.getQuestion(qId);
			String author = q.getCreatedBy().getUsername();
			if ( ! auth.getName().equals(author)) {
				redirectAttrs.addFlashAttribute("message", "You don't have the required permission to delete the question.");
				return "redirect:/questions/byMe";
			}
			
			redirectModifier = "/byMe";
		}
		
		String message;
		try {
			quizService.removeQuestion(qId);
			message = "The question has been removed.";
		} catch (Exception e) {
			message = "There had been an error while trying to remove the question.";
		}
		redirectAttrs.addFlashAttribute("message", message);
		return "redirect:/questions" + redirectModifier;
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

		model.addAttribute("texts", questionService.getQuestionTexts());
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
