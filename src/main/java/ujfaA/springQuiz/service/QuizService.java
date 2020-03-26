package ujfaA.springQuiz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ujfaA.springQuiz.model.Question;
import ujfaA.springQuiz.model.User;


@Service
public class QuizService {
	
	@Autowired 
	private QuestionService questionService;
	
	@Autowired
	private UserService userService;
	
	public void storeUsersAnswer(String username, Long questionId, String answer) {
		
		Question question = questionService.getQuestionById(questionId);
		User user = userService.getUser(username);
		user.storeAnsweredQuestion(question, answer);
		userService.update(user);
	}

	public int getUserScore(String username) {
		return userService.getScore(username);
	}
	
	public void removeQuestion(Long questionId) {
		userService.removeFromUsersAnswers(questionId);
		questionService.delete(questionId);
	}

	public void add(Question question, String username) {
		User user = userService.getUser(username);
		question.setCreatedBy(user);
		questionService.save(question);
	}

}
