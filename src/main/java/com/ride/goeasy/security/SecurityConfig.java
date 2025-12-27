package com.ride.goeasy.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.context.annotation.Bean;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final JwtFilter jwtFilter;

	public SecurityConfig(JwtFilter jwtFilter) {
		this.jwtFilter = jwtFilter;
	}

	// 🔐 Password Encoder Bean
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http
				// Disable CSRF for REST APIs
				.csrf(csrf -> csrf.disable())

				// State less session (JWT / REST)
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

				// Authorization rules
				.authorizeHttpRequests(auth -> auth
						// permit login
						.requestMatchers("/auth/**").permitAll()

						// Permit register API
						.requestMatchers("/driver/**").hasRole("DRIVER")

						.requestMatchers("/customer/**").hasRole("CUSTOMER")

						// Any other request must be authenticated
						.anyRequest().authenticated());
//	            .addFilterBefore(jwtfilter,UsernamePasswordAuthenticationfilter.class)

		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
}
