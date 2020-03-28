package ujfaA.springQuiz.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContextHolder;

import ujfaA.springQuiz.repository.UserRepository;

@Configuration
@EnableJpaAuditing
public class EntityAuditingConfig {
	
	@Autowired
	private UserRepository userRepository;
	
	@Bean
	public AuditorAware<User> auditorAware() {
		
		AuditorAware<User> currentAuditor = () -> {String signedInUser = SecurityContextHolder.getContext().getAuthentication().getName();
													return userRepository.findByUsername(signedInUser);};
		return currentAuditor;
		}
}
