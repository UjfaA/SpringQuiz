package ujfaA.springQuiz.repository;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;

import ujfaA.springQuiz.model.User;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {
	
	public Boolean existsUserByUsername(String username);

	public Optional<User> findByUsername(String username);

}
