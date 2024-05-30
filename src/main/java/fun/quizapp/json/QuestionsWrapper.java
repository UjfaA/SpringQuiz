package fun.quizapp.json;

import java.util.List;

import fun.quizapp.model.quiz.Question;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class QuestionsWrapper {

	List<Question> questions;
}
