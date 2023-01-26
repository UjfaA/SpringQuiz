package ujfaA.springQuiz.security;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import ujfaA.springQuiz.controller.QuizAdministratorController;
import ujfaA.springQuiz.controller.QuizController;
import ujfaA.springQuiz.controller.SecurityController;
import ujfaA.springQuiz.service.QuestionService;
import ujfaA.springQuiz.service.QuizService;
import ujfaA.springQuiz.service.UserService;


@WebMvcTest
@ContextConfiguration(classes = {LoginSuccessHandler.class, QuizController.class, QuizAdministratorController.class, SecurityController.class})
@Import(WebSecurityConfig.class)
class WebSecurityTest {
	
	@MockBean QuestionService questionService;
	@MockBean UserService userService;
	@MockBean QuizService quizService;
	
	@Autowired
	private WebApplicationContext context;
	
	
	private MockMvc mvc;
	
	@BeforeEach
	public void setup() {
		mvc = MockMvcBuilders
				.webAppContextSetup(context)
				.apply(SecurityMockMvcConfigurers.springSecurity())
				.build();
	}
	
	@Test
	@WithAnonymousUser
	void testAnonymous() throws Exception {
		mvc.perform(get("/registration"))
			.andExpect(status().isOk());
		mvc.perform(get("/quiz/completed"))
			.andExpect(redirectedUrlPattern("http*://**/login"));
	}
	
	@Test
	@WithMockUser(roles = "USER")
	void testBase() throws Exception {
		mvc.perform(get("/quiz/completed"))
			.andExpect(status().isOk());
		mvc.perform(get("/questions/byMe"))
			.andExpect(status().isForbidden());
	}
	
	@Test
	@WithMockUser(roles = {"CONTRIBUTOR"})
	void testContributor() throws Exception {
		mvc.perform(get("/quiz/completed"))
			.andExpect(status().isOk());
		mvc.perform(get("/questions/byMe"))
			.andExpect(status().isOk());
		mvc.perform(get("/questions"))
			.andExpect(status().isForbidden());
		mvc.perform(get("/users/usersEng"))
			.andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser(roles = "MODERATOR")
	void testModerator() throws Exception {
		mvc.perform(get("/quiz/completed"))
			.andExpect(status().isOk());
		mvc.perform(get("/questions"))
			.andExpect(status().isOk());
		mvc.perform(get("/users/usersEng"))
			.andExpect(status().isOk());
		mvc.perform(get("/users"))
			.andExpect(status().isForbidden());
	}
	
	@Test
	@WithMockUser(roles = {"ADMINISTRATOR"})
	void testAdministrator() throws Exception {
		mvc.perform(get("/quiz/completed"))
			.andExpect(status().isOk());
		mvc.perform(get("/users"))
			.andExpect(status().isOk());
	}

}
