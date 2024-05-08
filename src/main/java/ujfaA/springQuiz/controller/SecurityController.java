package ujfaA.springQuiz.controller;
/*

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ujfaA.springQuiz.model.Role;
import ujfaA.springQuiz.model.User;
import ujfaA.springQuiz.service.UserService;

@Controller
public class SecurityController {
	
	@Autowired
	private UserService userService;
	
	
	@GetMapping("/registration")
	public String registration(ModelMap model) {
		User user = new User();
		/*default role is administrator*/
/*
		user.setRole(Role.ADMINISTRATOR);
	    model.addAttribute(user);
	    return "registration";
	}
		
	@PostMapping("/registration")
	public String addNewUser( @Valid @ModelAttribute("user") User newUser, BindingResult bindingResult,
							ModelMap model, RedirectAttributes redirectAttributes) {

// TODO auto login when previously logged in  // Cannot perform login for 'admin2', already authenticated as 'user'
//			request.login(newUser.getUsername(), newUser.getPassword());
		
		if (bindingResult.hasErrors()) {
			return "registration";
		}
		
		if ( ! userService.usernameIsAvaible(newUser.getUsername()) ) {
			model.addAttribute("user", newUser);
			model.addAttribute("message", "The username isn't available.\nPlease pick another one.");
			return "registration";
		}
		if ( ! userService.emailIsAvaible(newUser.getEmail()) ) {
			model.addAttribute("user", newUser);
			model.addAttribute("message", "The email is already registered.\nPlease pick another one.");
			return "registration";
		}
		try {
			userService.register(newUser);
			redirectAttributes.addFlashAttribute("message", "You created a new account.");
			return "redirect:/login";
		} catch (Exception e) {
			model.addAttribute("user", newUser);
			model.addAttribute("message", "There was an error while trying to register this account.");
			return "registration";
		}
	}
	
	@GetMapping("/login")
	public String login(@RequestParam(defaultValue = "false") boolean error, ModelMap model) {
		if (error == true)
			model.addAttribute("message", "Wrong username or password.");
		return "login";
	}
	
}
*/
