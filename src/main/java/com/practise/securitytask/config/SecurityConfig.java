package com.practise.securitytask.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private PropertiesConfig propertiesConfig;
	
	@Bean
	static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		http.authorizeHttpRequests(request -> {
			request
			.requestMatchers("/security/api/v1/admin/dashboard").hasRole("ADMIN")
			.requestMatchers("/security/api/v1/user/dashboard").hasAnyRole("ADMIN","USER")
			.anyRequest().authenticated();
		})
		.httpBasic(Customizer.withDefaults())
		.formLogin((formLogin) ->
		formLogin
			.successHandler(authenticationSuccessHandler())
		);
		return http.build();
	}
	
	@Bean
	InMemoryUserDetailsManager setUsers() {
		int userSize = propertiesConfig.getUsers().size();
		List<UserDetails> users = new ArrayList<>();
		
		for(int i=0; i<userSize; i++) {
			UserDetails user = User.builder()
					.username(propertiesConfig.getUsers().get(i))
					.password(passwordEncoder().encode(propertiesConfig.getPasswords().get(i)))
					.roles(propertiesConfig.getRoles().get(i))
					.build();
			
			users.add(user);	
		}
		
		return new InMemoryUserDetailsManager(users);
	}
	
	@Bean
	AuthenticationSuccessHandler authenticationSuccessHandler() {
		return new CustomAuthenticationSuccessHandler();
	}
	
	private static class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

		@Override
		public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
				Authentication authentication) throws IOException, ServletException {
			if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
				response.sendRedirect("/security/api/v1/admin/dashboard");
			}else if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER"))) {
				response.sendRedirect("/security/api/v1/user/dashboard");
			}else {
				response.sendError(420);
			}
		}
		
	}

}
