package ujfaA.springQuiz.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.vote.RoleHierarchyVoter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import jakarta.servlet.DispatcherType;
import ujfaA.springQuiz.model.Role;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	
	@Autowired
    private LoginSuccessHandler loginSuccessHandler;
	
/* Spring Security 6 broke this roleHierarchy configuration. - Access is now defined by stating each role,
 * but RoleHierarchy is still used by thymeleaf-extras-springsecurity6 */
	@Bean
	public RoleHierarchy roleHierarchy(){
		var roleHierarchy = new RoleHierarchyImpl();
		roleHierarchy.setHierarchy( "ROLE_ADMINISTRATOR > ROLE_MODERATOR > ROLE_CONTRIBUTOR > ROLE_USER" );
		return roleHierarchy;
	}
/* https://docs.spring.io/spring-security/reference/servlet/authorization/architecture.html#authz-hierarchical-roles */	
	@Bean
	AccessDecisionVoter hierarchyVoter(RoleHierarchy roleHierarchy) {
	    return new RoleHierarchyVoter(roleHierarchy);
	}

/*
	@Bean
	public DefaultWebSecurityExpressionHandler webSecurityExpressionHandler( RoleHierarchy hierarchy) {
		var webSecurityExpressionHandler = new DefaultWebSecurityExpressionHandler();
		webSecurityExpressionHandler.setRoleHierarchy(hierarchy);
		return webSecurityExpressionHandler;
	}

	@Bean
	public WebSecurityCustomizer roleHierarchySecurityCustomizer( DefaultWebSecurityExpressionHandler webSecurityExpressionHandler, RoleHierarchy roleHierarchy) { 
		webSecurityExpressionHandler.setRoleHierarchy(roleHierarchy());
		return web -> web.expressionHandler(webSecurityExpressionHandler);
	}
*/ 
	
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
				.hasAnyRole(Role.CONTRIBUTOR.name(), Role.MODERATOR.name(), Role.ADMINISTRATOR.name())
			.requestMatchers("/questions", "/users/usersEng")
				.hasAnyRole(Role.MODERATOR.name(), Role.ADMINISTRATOR.name())
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
