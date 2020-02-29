package ujfaA.springQuiz.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	
	public List<User> listAll() {
		List<User> list = new ArrayList<User>();
		userRepo.findAll().forEach(list::add);
		return list;
	}
	
	public void deleteUser(User user) {
		userRepo.delete(user);
	}
}
