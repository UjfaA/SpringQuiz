package fun.quizapp.ai;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.jr.ob.JSON;

import fun.quizapp.model.QuizService;
import fun.quizapp.model.quiz.Question;
import fun.quizapp.model.quiz.Quiz;

@Service
public class AIService {

	private final QuizService	service;
	private final ChatClient	chat;
	
	private final String		prompt;
	private final List<String>	topics;


	public AIService(
			QuizService service,
			OpenAiChatModel chatModel,
			@Qualifier("prompt") String prompt,
			@Qualifier("topics") List<String> topics) {

		this.service = service;
		this.chat	= ChatClient.create(chatModel);
		this.prompt = prompt;
		this.topics = topics;
	}
	
	public long generateQuiz( String topic ) {
		
		Quiz quiz = new Quiz();
		quiz.setTitle(topic);
		quiz.setSubtitle("AI generated quiz about " + topic);
		
		quiz = service.add( quiz, generateQuestions( prompt.formatted(topic) ) );
		return quiz.getId();
	}
	
	private List<Question> generateQuestions(String promt) {
		try {
			String questions = chat.prompt( new Prompt(promt) ).call().content();
			return JSON.std.listOfFrom(Question.class, questions);
		} catch(IOException ex) {
			return Collections.emptyList();
		}
	}
	
	public Set<String> getTopics( int limit ) {
		
		if (topics.size() < limit) return Set.copyOf(topics);

		Random rand = new Random();
		Set<String> top = new HashSet<>();
		while ( top.size() < limit ) {
			int index = rand.nextInt( this.topics.size() );
			top.add( this.topics.get(index) );
		}
		return top;
	}
	
}
