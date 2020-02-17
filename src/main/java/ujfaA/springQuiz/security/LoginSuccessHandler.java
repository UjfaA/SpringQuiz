package ujfaA.springQuiz.security;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import ujfaA.springQuiz.model.User;
import ujfaA.springQuiz.service.UserService;

@Component
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	
	@Autowired
	private UserService userService;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		super.onAuthenticationSuccess(request, response, authentication);
		
		User user = userService.getUser(authentication.getName());
		user.setLastActive(LocalDateTime.now());
		user = userService.update(user);
		
		HttpSession session = request.getSession(true);
		session.setAttribute("userFirstName", user.getFirstName());
	}

	@Override
	protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response) {
		return "/home";
	}
}
