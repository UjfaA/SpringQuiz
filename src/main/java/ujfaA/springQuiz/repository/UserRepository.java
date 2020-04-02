package ujfaA.springQuiz.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import ujfaA.springQuiz.dto.UserDTO;
import ujfaA.springQuiz.model.User;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {
	
	public Boolean existsUserByUsername(String username);
	
	public boolean existsUserByEmail(String email);

	public Optional<User> findByUsername(String username);
	
	@Query("SELECT NEW ujfaA.springQuiz.dto.UserDTO(u.username, u.email, u.firstName, u.lastName)"
			+ " FROM User u")
	public Set<UserDTO> getUsersInfo();
	
	public int countCorrectAnswers(String username);
	
	@Query("SELECT u.username"
			+ " FROM User u, IN(u.answers) ans"
			+ " WHERE KEY(ans).id = :questionId")
	public Set<String> getUsernamesThatAnswered(long questionId);
	
	@Query("SELECT u.username"
			+ " FROM User u, IN(u.answers) ans"
			+ " WHERE KEY(ans).id = :questionId AND ans = :answer")
	public Set<String> getUsernamesThatAnsweredWith(long questionId, String answer);
	
	@Query(nativeQuery = true,
		value = "SELECT users.username"
			+ " FROM users RIGHT JOIN ("
			+ " 	SELECT user_id, count(*) AS questionsAnswered"
			+ "		FROM users_answers"
			+ "		GROUP BY user_id ) AS t1"
			+ "	ON users.user_id = t1.user_id"
			+ " WHERE questionsAnswered = (SELECT count(*) FROM questions)")
	public Set<String> getUsernamesThatAnsweredEveryQuestion();
	
	@Query(nativeQuery = true,
		value = "SELECT users.username"
			+ " FROM users RIGHT JOIN ("
			+ " 	SELECT user_id, count(*) AS questionsAnsweredCorrectly"
			+ "		FROM users_answers NATURAL JOIN questions"
			+ "		WHERE answer = correct_answer"
			+ "		GROUP BY user_id ) AS t1"
			+ "	ON users.user_id = t1.user_id"
			+ " WHERE questionsAnsweredCorrectly = (SELECT count(*) FROM questions)")
	public Set<String> getUsernamesThatCorrectlyAnsweredEveryQuestion();
	
	@Modifying
	@Query(nativeQuery = true,
		value = "DELETE FROM users_answers WHERE question_id = ?1")
	public void removeFromUsersAnswers(long questionId);

}