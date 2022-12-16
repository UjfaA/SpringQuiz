package ujfaA.springQuiz.security;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ujfaA.springQuiz.repository.UserRepository;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		ujfaA.springQuiz.model.User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
		Set<GrantedAuthority> grantedAuthorities = Set.of( new SimpleGrantedAuthority("ROLE_" + user.getRole()) );
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
	}

}
