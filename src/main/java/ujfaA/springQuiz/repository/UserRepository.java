package ujfaA.springQuiz.repository;

import java.util.List;
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
	
	@Query("SELECT NEW ujfaA.springQuiz.dto.UserDTO(u.username, u.email, u.firstName, u.lastName, u.role, u.lastActive)"
			+ " FROM User u")
	public Set<UserDTO> getUsersInfo();
	
	public int countCorrectAnswers(String username);
	
	@Query("SELECT u.username"
			+ " FROM User u, IN(u.answers) ans"
			+ " WHERE KEY(ans).id = :questionId AND ans <> '' ")
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
			+ "		WHERE answer <> '' "
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
	
	@Query("SELECT count (answer)"
			+ " FROM User u, In(u.answers) ans"
			+ " WHERE KEY(ans).id = :questionId AND ans = '' ")
	public int skippedCount(long questionId);
	
	@Query(nativeQuery = true,
		value = "SELECT count(users_answers.answer)"
			+ " FROM answers left join users_answers on answers.answer = users_answers.answer AND answers.question_id = users_answers.question_id"
			+ "	WHERE answers.question_id = :questionId"
			+ "	GROUP BY answers.answer"
			+ " ORDER BY ordinal")
	public List<Integer> answersChosenCount(long questionId);


}