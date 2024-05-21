package fun.quizapp.model;

import java.util.ArrayList;
import java.util.List;

import fun.quizapp.model.quiz.Quiz;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DataRoot {

	private List<Quiz> quizes = new ArrayList<>();

}
