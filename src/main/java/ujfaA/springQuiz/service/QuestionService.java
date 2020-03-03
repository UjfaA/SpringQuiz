package ujfaA.springQuiz.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ujfaA.springQuiz.model.Question;
import ujfaA.springQuiz.repository.QuestionRepository;

@Service
@Transactional
public class QuestionService {
	
	@Autowired
	private QuestionRepository questionRepo;
	
	public Iterable<Question> listAll() {
		return questionRepo.findAll();
	}
	
	public int getNumberOfQuestions() {
		return (int) questionRepo.count();
	}
	
	public Question getQuestionById(long id) {
		return questionRepo.findById(id).orElseThrow();
	}
	
	public Question getQuestionByQuestionText(String questionText) {
		return questionRepo.findByQuestionText(questionText);
	}
		
	public Question getQuestionByIndex(int qIndex) {
		
		Pageable pageRequest = PageRequest.of(qIndex, 1);
		Page<Question> page = questionRepo.findByOrderById(pageRequest);
		if ( ! page.hasContent())
			throw new IndexOutOfBoundsException();
		return page.getContent().get(0);
	}
	
	public Question save(Question q) {
		
		List<String> ansList = q.getAnswers();
		String correctAnswer = ansList.get(q.getSelectedAnswerIndex());  
		q.setCorrectAnswer(correctAnswer);

		return questionRepo.save(q);
	}
	
	public void delete(Long id) {
		questionRepo.deleteById(id);
	}
	
	public Set<String> GetQuestionsText() {
		return questionRepo.findAllQuestionTexts();
	}

	public boolean containsRepeatedAnswers(Question question) {
		Set<String> set = new HashSet<String>(question.getAnswers());
		return set.size() != question.getAnswers().size();
	}

}
