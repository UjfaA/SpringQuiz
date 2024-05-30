package fun.quizapp.ai;

import java.util.Collections;
import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import fun.quizapp.json.QuestionsWrapper;
import fun.quizapp.model.QuizService;
import fun.quizapp.model.quiz.Question;
import fun.quizapp.model.quiz.Quiz;

@Service
public class AIService {

	private final ChatClient chat;
	private final QuizService service;
	private final ObjectMapper	mapper;
	
	private final String promt;

	public AIService(QuizService service, @Value("${quiz.promt}") String promt) {
		this.service = service;
		this.promt = promt;
		this.mapper = new ObjectMapper();
		String key = System.getenv("SPRING_AI_OPENAI_CHAT_API_KEY");
		OpenAiApi openAIApi = new OpenAiApi(key);
		this.chat =  ChatClient.create( new OpenAiChatModel(openAIApi));
	}

	public void generateQuiz( String topic) {

		Quiz quiz = new Quiz();
		quiz.setTitle(topic);
		quiz.setSubtitle("AI generated quiz about " + topic.toLowerCase());

		service.add( quiz, generateQuestions( promt.formatted(topic)) );
	}

	private List<Question> generateQuestions(String promt) {
		try {
			String questions = chat.prompt( new Prompt(promt) ).call()
						.chatResponse().getResult().getOutput().getContent();
			return mapper.readValue( questions, QuestionsWrapper.class)
						.getQuestions();
		} catch (JsonProcessingException e) {
			return Collections.emptyList();
		}
	}

}
