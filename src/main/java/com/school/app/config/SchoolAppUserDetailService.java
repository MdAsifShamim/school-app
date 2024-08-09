package com.school.app.config;

import com.school.app.model.User;
import com.school.app.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SchoolAppUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("invalid user Name"));
        List<GrantedAuthority> authorities = user.getAuthorities().stream()
                .map(authority ->new SimpleGrantedAuthority(authority.getAuthority()))
                .collect(Collectors.toUnmodifiableList());
        return new org.springframework.security.core.userdetails.User
                (user.getEmail(), user.getPwd(), authorities);

    }
}
