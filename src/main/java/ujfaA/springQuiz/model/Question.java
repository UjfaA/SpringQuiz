package ujfaA.springQuiz.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name="QUESTIONS")
@Getter @Setter
public class Question{
	
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(unique = true, nullable = false)
	private String questionText;
	
	@Column(nullable = false)
	private String correctAnswer;
	
	@Transient
	private int correctAnswerIndex;
	
	@ElementCollection(fetch = FetchType.EAGER)
	private List<String> answers = new ArrayList<String>();
	
	@ManyToMany(mappedBy = "questionsAnswered")
	private Set<User> usersAnswered = new HashSet<User>();
	
	@ManyToMany(mappedBy = "questionsAnsweredCorrectly")
	private Set<User> usersAnsweredCorrectly = new HashSet<User>();
	
	
	public Question() {
	}

	public double getCorrectnesstPercent() {
		if (usersAnswered.isEmpty()) 
			return -1.0;
		else
			return (usersAnsweredCorrectly.size() * 1.0) / usersAnswered.size();
	}
	
	@Override
	public String toString() {
		return questionText;
	}
	
	@Override
	public boolean equals(Object other) {
		if ( ! (other instanceof Question))
			return false;
		Question otherQ = (Question) other;
		return this.questionText.equals(otherQ.questionText);
	}
	
	@Override
	public int hashCode() {
		return this.questionText.hashCode();
	}
}
