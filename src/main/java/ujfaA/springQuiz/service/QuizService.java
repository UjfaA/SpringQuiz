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
	
	
	public void storeUsersAnswer(String username, long questionId, String answer) {
		
		Question question = questionService.getQuestionEntity(questionId);
		User user = userService.getUser(username);
		user.storeAnsweredQuestion(question, answer);
		userService.update(user);
	}

	public int getUserScore(String username) {
		return userService.getScore(username);
	}
	
	public void removeQuestion(long id) {
		userService.removeFromUsersAnswers(id);
		questionService.delete(id);
	}
	
}
