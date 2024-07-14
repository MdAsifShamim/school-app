package com.school.app.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.school.app.model.User;
import com.school.app.repository.UserRepository;

@Component
public class SchoolAppUserNamePwdAuthProvider implements AuthenticationProvider {

	@Autowired
	UserRepository userRepository;
	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String userName = authentication.getName();
		String password = authentication.getCredentials().toString();
		List<User> loginList = userRepository.findByEmail(userName);
		if (loginList.size() > 0) {
			if (passwordEncoder.matches(password, loginList.get(0).getPwd())) {
				List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
				authorityList.add(new SimpleGrantedAuthority(loginList.get(0).getRole()));
				return new UsernamePasswordAuthenticationToken(userName, password, authorityList);
			} else {
				throw new BadCredentialsException("password incorrect!!!");
			}

		} else {
			throw new BadCredentialsException("User does not exsist");
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {

		return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
	}

}
