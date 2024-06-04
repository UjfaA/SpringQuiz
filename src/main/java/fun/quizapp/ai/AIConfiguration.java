package fun.quizapp.ai;

import static org.springframework.util.StreamUtils.copyToString;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
public class AIConfiguration {

	@Bean
	OpenAiChatModel openAiChatModel() {
		String key = System.getenv("SPRING_AI_OPENAI_CHAT_API_KEY");
		return new OpenAiChatModel(new OpenAiApi(key));
	}

	@Bean
	String prompt( @Value("${quiz.prompt}") String prompt ) {
		return prompt;
	}

	@Bean
	List<String> topics( @Value("classpath:topics.txt") Resource topics ) {
		try ( InputStream input = topics.getInputStream() ) {
			return copyToString( input, StandardCharsets.UTF_8 ).lines().toList();
		} catch (Exception e) {
			return List.of("Capital Cities", "Animals", "Planets and Moons", "Historical Figures");
		}
	}

}
