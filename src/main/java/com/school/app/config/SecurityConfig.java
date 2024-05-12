package com.school.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import  org.springframework.security.config.Customizer;

@Configuration
public class SecurityConfig {

	
	@Bean
	SecurityFilterChain customeSecurityFilter(HttpSecurity httpSequrity) throws Exception{
		httpSequrity
		.csrf(csrf->csrf.disable())
		.authorizeHttpRequests(request->request.requestMatchers("/sch/app/v1/**").permitAll());
		httpSequrity.formLogin(Customizer.withDefaults());
		//httpSequrity.httpBasic(Customizer.withDefaults());
		return httpSequrity.build();
	}
	
//	@Bean
//	PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}


}
