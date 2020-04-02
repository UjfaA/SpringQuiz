package ujfaA.springQuiz.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ujfaA.springQuiz.dto.QuestionDTO;
import ujfaA.springQuiz.dto.UserDTO;
import ujfaA.springQuiz.model.User;
import ujfaA.springQuiz.repository.UserRepository;

@Service
@Transactional
public class UserService {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public boolean usernameIsAvaible(String username) {
		return ( ! userRepo.existsUserByUsername(username));
	}
	
	public User getUser(String username) {
		return userRepo.findByUsername(username).orElseThrow();
	}
	
	public User register(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		return userRepo.save(user);
	}
	
	public User update(User user) {
		return userRepo.save(user);
	}
	
	public Set<UserDTO> getUsersInfo() {
		return userRepo.getUsersInfo();
	}
	
	public void deleteUser(User user) {
		userRepo.delete(user);
	}

	public Set<String> getUsernamesThatAnswered(QuestionDTO q, boolean correctly ) {
		if (correctly)
			return userRepo.getUsernamesThatAnsweredWith(q.getId(), q.getCorrectAnswer());
		else
			return userRepo.getUsernamesThatAnswered(q.getId());
	}

	public Set<String> getUsernamesThatAnsweredEveryQ(boolean correctly) {
		if (correctly)
			return userRepo.getUsernamesThatCorrectlyAnsweredEveryQuestion();
		else
			return userRepo.getUsernamesThatAnsweredEveryQuestion();
	}
	
	public int getScore(String username) {
		return userRepo.countCorrectAnswers(username);
	}
	
	public void removeFromUsersAnswers(long questionId) {
		userRepo.removeFromUsersAnswers(questionId);
	}

}
