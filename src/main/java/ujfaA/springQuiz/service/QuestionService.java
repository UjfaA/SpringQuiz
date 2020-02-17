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
	
	public List<Question> listAll() {
	/*
		List<Question> list = new ArrayList<Question>();
		questionRepo.findAll().forEach(list::add);
		return list;
	*/
		return questionRepo.findAll();
	}

	public Question getQuestionByQuestionText(String questionText) {
		return questionRepo.findByQuestionText(questionText);
	}
	
	public int getNumberOfQuestions() {
		return (int) questionRepo.count();
	}
	
	public Question getQuestionByIndex(int qindex) {
		
		Pageable pageRequest = PageRequest.of(qindex, 1);
		Page<Question> page = questionRepo.findByOrderById(pageRequest);
		if ( ! page.hasContent())
			throw new IndexOutOfBoundsException();
		return page.getContent().get(0);
	}
	
	public Question save(Question q) {
		
		List<String> ans = q.getAnswers();
		String correctAnswer = ans.get(q.getCorrectAnswerIndex());  
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

/*
	public List<Question> listAllOrderedByCorrectnes() {
		List<Question> questions = this.listAll();
		questions.sort((q1, q2) -> { return Double.compare(	q2.getCorrectnesstPercent(),
															q1.getCorrectnesstPercent() );
		});
		return questions;
	}
*/
}
