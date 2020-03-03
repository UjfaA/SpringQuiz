package ujfaA.springQuiz.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import ujfaA.springQuiz.model.Question;
import ujfaA.springQuiz.model.User;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {
	
	
	public Boolean existsUserByUsername(String username);

	public Optional<User> findByUsername(String username);
	
	public int countCorrectAnswers(String username);
	
	@Query("SELECT u.username"
			+ " FROM User u, IN(u.answers) ans"
			+ " WHERE KEY(ans) = :question")
	public Set<String> getUsernamesThatAnswered(Question question);
	
	@Query("SELECT u.username"
			+ " FROM User u, IN(u.answers) ans"
			+ " WHERE KEY(ans) = :question AND ans = :answer")
	public Set<String> getUsernamesThatAnsweredWith(Question question, String answer);
}