package ujfaA.springQuiz.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import jakarta.servlet.DispatcherType;
import ujfaA.springQuiz.model.Role;

@Configuration
public class WebSecurityConfig {
	
	@Autowired
    private LoginSuccessHandler loginSuccessHandler;
	
	@Bean
	public RoleHierarchy roleHierarchy(){
		var roleHierarchy = new RoleHierarchyImpl();
		roleHierarchy.setHierarchy( "ROLE_ADMINISTRATOR > ROLE_MODERATOR > ROLE_CONTRIBUTOR > ROLE_USER" );
		return roleHierarchy;
	}

	@Bean
	protected SecurityFilterChain filterChain( HttpSecurity http) throws Exception {
		
		http
		.csrf().disable()
		.authorizeHttpRequests()
			.shouldFilterAllDispatcherTypes(false)
			.dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
			.requestMatchers("/registration","/about","/errpage")
				.permitAll()
			.requestMatchers("/","/home", "/quiz/**")
				.authenticated()
			.requestMatchers("/questions/byMe", "/questions/new","/questions/{qId:[0-9]+}", "/questions/delete")
				.hasRole(Role.CONTRIBUTOR.name())
			.requestMatchers("/questions", "/users/usersEng")
				.hasRole(Role.MODERATOR.name())
			.anyRequest()
				.hasRole(Role.ADMINISTRATOR.name())
		.and()
		.formLogin()
			.loginPage("/login").permitAll()
			.successHandler(loginSuccessHandler)
			.failureUrl("/login?error=true")
		.and()
		.logout()
			.logoutSuccessUrl("/");
		
		return http.build();
	}

}
