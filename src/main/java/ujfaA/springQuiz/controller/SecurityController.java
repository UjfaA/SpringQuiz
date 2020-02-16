package ujfaA.springQuiz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ujfaA.springQuiz.model.User;
import ujfaA.springQuiz.service.UserService;

@Controller
public class SecurityController {
	
	@Autowired
	private UserService userService;
	
	
	@GetMapping("/registration")
	public String registration(ModelMap model) {
	    model.addAttribute(new User());
	    return "registration";
	}
		
	@PostMapping("/registration")
	public String addNewUser( ModelMap model, @ModelAttribute("user") User newUser) {

// TODO auto login when previously logged in  // Cannot perform login for 'admin2', already authenticated as 'user'
//			request.login(newUser.getUsername(), newUser.getPassword());
		
		if( userService.usernameIsAvaible(newUser.getUsername()) ) {
			userService.register(newUser);
			return "redirect:/login";
		}
		else {
			model.addAttribute("user", newUser);
			model.addAttribute("message", "The username isn't available.\nPlease pick a one that's not taken.");
			return "registration";
		}
	}
	
	@GetMapping("/login")
	public String login(@RequestParam(defaultValue = "false") boolean error,
						@RequestParam(required = false) String message,
						ModelMap model) {
		if (error == true)
			message = "Wrong username or password.";
		model.addAttribute("message", message);
		return"login";
	}
}
