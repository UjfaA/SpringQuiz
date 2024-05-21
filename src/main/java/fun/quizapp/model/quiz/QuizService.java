package fun.quizapp.model.quiz;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.eclipse.store.integrations.spring.boot.types.concurrent.Read;
import org.eclipse.store.storage.embedded.types.EmbeddedStorageManager;
import org.springframework.stereotype.Service;

import fun.quizapp.model.DataRoot;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class QuizService {

	private final EmbeddedStorageManager storage;

	@Read
	public Stream<Quiz> getAll() {
		var data = (DataRoot) storage.root();
//		return new ArrayList<>(data.getQuizes()).stream();
		return  new ArrayList<>( List.of( new Quiz(), new Quiz() )).stream(); 
	}
	/*
	
	private QuestionService questionService;
	
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
	
	*/
}