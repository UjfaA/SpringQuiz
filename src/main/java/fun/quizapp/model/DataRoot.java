package fun.quizapp.model;

import static lombok.AccessLevel.PACKAGE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.eclipse.serializer.reference.Lazy;

import fun.quizapp.model.quiz.Question;
import fun.quizapp.model.quiz.Quiz;
import lombok.Getter;
import lombok.experimental.Accessors;

@Accessors(fluent = true)
@Getter(PACKAGE)
public class DataRoot {

	private final AtomicLong nextQuizId = new AtomicLong(12345);

	private final List<Quiz> quizes = new ArrayList<>();

	private final HashMap<Long, Lazy<List<Question>>> questions = new HashMap<>();

}
