package com.cristinelpavel.authorizationserver;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SecurityConfig {
	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		return http
				.authorizeRequests(authorizeRequests -> authorizeRequests.anyRequest().authenticated())

				.formLogin()

				.and().build();
	}

	@Bean
	UserDetailsService userDetailsService(UserRepository userRepo) {
		return username -> userRepo.findByUsername(username);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public ApplicationRunner dataLoader(UserRepository repo, PasswordEncoder encoder) {
		return args -> {
			repo.save(new User("habuma", encoder.encode("password"), "ROLE_ADMIN", null, null, null, null, null));
			repo.save(new User("tacochef", encoder.encode("password"), "ROLE_ADMIN", null, null, null, null, null));
		};
	}
}
