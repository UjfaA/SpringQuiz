package ujfaA.springQuiz.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ujfaA.springQuiz.dto.QuestionDTO;
import ujfaA.springQuiz.model.Question;
import ujfaA.springQuiz.repository.QuestionRepository;

@Service
@Transactional
public class QuestionService {
	
	@Autowired
	private QuestionRepository questionRepo;


	public Set<QuestionDTO> listAll() {
		return questionRepo.findBy();
	}

	public Set<QuestionDTO> listAllByUser(String username) {
		return questionRepo.findByCreatedByUsername(username);
	}

	public int getNumberOfQuestions() {
		return (int) questionRepo.count();
	}

	public QuestionDTO getQuestion(long questionId) {
		return questionRepo.getDtoById(questionId);
	}

	Question getQuestionEntity(long id) {
		return questionRepo.findById(id).orElseThrow();
	}

	public boolean exist(Question question) {
		Set<Question> fromDB = questionRepo.findByQuestionText(question.getQuestionText());
		return fromDB.contains(question);
	}

	public QuestionDTO getQuestionByIndex(int qIndex) {
		
		Pageable pageRequest = PageRequest.of(qIndex, 1);
		Page<QuestionDTO> page = questionRepo.findByOrderById(pageRequest);
		if ( ! page.hasContent())
			throw new IndexOutOfBoundsException();
		return page.getContent().get(0);
	}
	
	public Question save(Question q) {

		int markedAsCorrect = q.getSelectedAnswerIndex();  
		String correctAnswer = q.getAnswers().get(markedAsCorrect);
		q.setCorrectAnswer(correctAnswer);
		return questionRepo.save(q);
	}
	
	public void delete(Long id) {
		questionRepo.deleteById(id);
	}
	
	public List<String> getQuestionTexts() {
		return questionRepo.findAllQuestionTexts();
	}
	
}
