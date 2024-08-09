package com.school.app.config;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.school.app.repository.UserRepository;

@Component
@RequiredArgsConstructor
@Profile("!prod")
public class SchoolAppUserNamePwdAuthProvider implements AuthenticationProvider {


    private final SchoolAppUserDetailService userDetailService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String userName = authentication.getName();
        String password = authentication.getCredentials().toString();
        UserDetails user = userDetailService.loadUserByUsername(userName);
        return new UsernamePasswordAuthenticationToken(userName, password, user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {

        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }

}
