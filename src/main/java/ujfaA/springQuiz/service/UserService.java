package ujfaA.springQuiz.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ujfaA.springQuiz.model.Question;
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
		user.setRole(user.isAdministrator()	? "ADMIN"
											: "USER" );
		return userRepo.save(user);
	}
	
	public User update(User user) {
		return userRepo.save(user);
	}
	
	public Iterable<User> listAll() {
		return userRepo.findAll();
	}
	
	public void deleteUser(User user) {
		userRepo.delete(user);
	}

	public Set<String> getUsernamesThatAnswered(Question q, boolean correctly ) {
		if (correctly)
			return userRepo.getUsernamesThatAnsweredWith(q, q.getCorrectAnswer());
		else
			return userRepo.getUsernamesThatAnswered(q);
	}

	public HashSet<String> getUsernamesThatAnsweredEveryQ(boolean correctly) {
		// TODO Auto-generated method stub
		return new HashSet<String>();
	}
	
	public int getScore(String username) {
		return userRepo.countCorrectAnswers(username);
	}

}
