package ujfaA.springQuiz.dto;

import java.util.List;

/**
 * @see <a href="https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#projections.interfaces"> More about interface-based projections </a>
 */
public interface QuestionDTO {
	
	long getId();
	CreatedBy getCreatedBy();
	String getQuestionText();
	String getCorrectAnswer();
	List<String> getAnswers();
	
	public interface CreatedBy {
		String getUsername();
	}

}
