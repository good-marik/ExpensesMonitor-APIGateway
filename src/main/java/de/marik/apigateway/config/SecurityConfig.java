package de.marik.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import de.marik.apigateway.services.PersonDetailsService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
	private final PersonDetailsService personDetailsService;

	public SecurityConfig(PersonDetailsService personDetailsService) {
		this.personDetailsService = personDetailsService;
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(auth -> auth
			.requestMatchers("/", "/home", "/auth/login", "/error", "/auth/registration", "/css/dark.css").permitAll()
			.anyRequest().authenticated())
//			.formLogin(withDefaults())
			.formLogin(login -> login.loginPage("/auth/login")
				.loginProcessingUrl("/process_login")
				.defaultSuccessUrl("/show", true)
				.failureUrl("/auth/login?error"))
//			.httpBasic(withDefaults())
//			.csrf(csrf -> csrf.disable())
	        .logout(out -> out.logoutUrl("/logout")
	        	.logoutSuccessUrl("/auth/login"));
		return http.build();
	}

	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(personDetailsService).passwordEncoder(getPasswordEncoder());
	}

	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
