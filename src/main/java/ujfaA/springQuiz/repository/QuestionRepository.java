package ujfaA.springQuiz.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import ujfaA.springQuiz.model.Question;


public interface QuestionRepository extends PagingAndSortingRepository<Question, Long> {
	
	
	public Question findByQuestionText(String questionText);

	public Page<Question> findByOrderById(Pageable pageable);
	
	@Query("SELECT q.questionText FROM Question q ORDER BY q.id")
	public List<String> findAllQuestionTexts();

}