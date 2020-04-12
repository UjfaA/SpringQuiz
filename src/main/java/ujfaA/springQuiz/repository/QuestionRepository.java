package ujfaA.springQuiz.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import ujfaA.springQuiz.dto.QuestionDTO;
import ujfaA.springQuiz.model.Question;


public interface QuestionRepository extends PagingAndSortingRepository<Question, Long> {

	public Set<QuestionDTO> findBy();
	
	public Set<QuestionDTO> findByCreatedByUsername(String username);

	public Set<Question> findByQuestionText(String questionText);

	public Page<QuestionDTO> findByOrderById(Pageable pageRequest);

	@Query("SELECT q.questionText FROM Question q ORDER BY q.id")
	public List<String> findAllQuestionTexts();

}