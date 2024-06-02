package fun.quizapp.model;

import static java.util.List.copyOf;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.eclipse.serializer.reference.Lazy;
import org.eclipse.store.integrations.spring.boot.types.concurrent.Read;
import org.eclipse.store.integrations.spring.boot.types.concurrent.Write;
import org.eclipse.store.storage.embedded.types.EmbeddedStorageManager;
import org.springframework.stereotype.Service;

import fun.quizapp.model.quiz.Answer;
import fun.quizapp.model.quiz.Question;
import fun.quizapp.model.quiz.Quiz;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class QuizService {

	private final EmbeddedStorageManager storage;
	
	@Read
	public Stream<Quiz> getAll() {
		return copyOf( data().quizes() ).stream();
	}
	
	@Read
	public Stream<Question> getQuestions(Long quizId) {
		return copyOf( data().questions().get(quizId).get() ).stream();
	}

	@Read
	public Optional<Quiz> getById( long id ) {
		return data().quizes().stream()
				.filter( q -> q.getId() == id)
				.map(Quiz::clone)
				.findAny();
	}

	@Write
	public Quiz add( Quiz quiz, List<Question> questions ) {
		var data = data();
		quiz.setId( data.nextQuizId().getAndIncrement() );
		data.quizes().add(quiz);
		data.questions().put( quiz.getId(), Lazy.Reference(new ArrayList<>(questions)) );
		storage.storeAll( data.quizes(), data.questions(), data.nextQuizId() );
		return quiz;
	}

	@Write
	public void remove(Quiz quiz) {
		var data = data();
		data.quizes().removeIf( q -> q.getId() == quiz.getId());
		data.questions().remove( quiz.getId() );
		storage.storeAll( data.quizes(), data.questions() );
	}
	
	@Write
	public void nukeIt() {
		var data = data();
		data.quizes().clear();
		data.questions().clear();
		storage.storeAll(data.quizes(), data.questions());
	}
	
	private DataRoot data() {
		return (DataRoot) storage.root();
	}

	public static List<Question> dummyQuestions() {
		var q1 = new Question();
		var q2 = new Question();
		var q3 = new Question();

		q1.getAnswers().add(new Answer("A1"));
		q1.getAnswers().add(new Answer("B1"));
		q1.getAnswers().add(new Answer("C1"));

		q2.getAnswers().add(new Answer("A2"));
		q2.getAnswers().add(new Answer("B2"));
		q2.getAnswers().add(new Answer("C2"));

		q3.getAnswers().add(new Answer("A3"));
		q3.getAnswers().add(new Answer("B3"));
		q3.getAnswers().add(new Answer("C3"));

		var questions = List.of(q1, q2, q3);
		questions.forEach( q -> { 
			q.setQuestionText("What is the answer?");
			q.getAnswers().get(2).setCorrect(true);
		});

		return questions;
	}

}