package ujfaA.springQuiz.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import ujfaA.springQuiz.model.Question;


public interface QuestionRepository extends PagingAndSortingRepository<Question, Long> {
	
	@Override
	List<Question> findAll();
	
	public Question findByQuestionText(String questionText);

	public Page<Question> findByOrderById(Pageable pageable);
	
	@Query("SELECT q.questionText FROM Question q")
	public Set<String> findAllQuestionTexts();

}
