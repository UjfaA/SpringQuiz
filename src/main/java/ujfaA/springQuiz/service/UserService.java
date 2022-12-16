package ujfaA.springQuiz.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private PasswordEncoder bCryptPasswordEncoder;
	
	public boolean usernameIsAvaible(String username) {
		return ( ! userRepo.existsUserByUsername(username));
	}
	
	public boolean emailIsAvaible(String email) {
		return ( ! userRepo.existsUserByEmail(email));
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

	public Float getAnsweredPercentage(long questionId) {
		
		List<Integer> ansCount = userRepo.answersChosenCount(questionId);
		int totalAnswers = 0;
			for (Integer i : ansCount) {
				totalAnswers += i;
			}
		int timesSkipped = userRepo.skippedCount(questionId);
		if (totalAnswers + timesSkipped != 0)
			return ((float) totalAnswers) / (totalAnswers + timesSkipped);
		else
			return -1.0f;
	}
	
	public List<Float> getAnswersDistribution(long questionId) {
		
		 List<Integer> ansCount = userRepo.answersChosenCount(questionId);
		 int totalAnswers = 0;
		 for (Integer i : ansCount) {
			 totalAnswers += i;
		 }
		 List<Float> ansDistribution = new ArrayList<>();
		 for (Integer i : ansCount) {
			 if (totalAnswers == 0) {
				 ansDistribution.add(0.0f);
				 continue;
			 }
			 ansDistribution.add( ((float) i )/ totalAnswers);
		 }
		 return ansDistribution;
	}

}
