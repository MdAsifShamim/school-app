package com.school.app.controllers;

import java.time.LocalDateTime;
import java.util.Optional;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.school.app.model.User;
import com.school.app.repository.UserRepository;

@RestController
@RequestMapping("/app")
@AllArgsConstructor
public class RegistrationRestController {


    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @PostMapping("/v1/register-new-user")
    public ResponseEntity<String> registerNewUser(@RequestBody User user) {

        ResponseEntity<String> response = null;
        User updatedUser = null;
        try {
            String pwd = passwordEncoder.encode(user.getPwd());
            user.setPwd(pwd);
            user.setCreateDt(LocalDateTime.now());
            updatedUser = userRepository.save(user);
            if (updatedUser.getUserId() > 0) {
                response = ResponseEntity.status(HttpStatus.CREATED).body("Given user registration Detail Saved Successfully");
            }
        } catch (Exception ex) {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An exception occur due to " + ex.getMessage());
        }
        return response;
    }

    @RequestMapping("/v1/user")
    public User getUserDetailsAfterLogin(Authentication authentication) {
        Optional<User> optionalUser = userRepository.findByEmail(authentication.getName());
        return optionalUser.orElse(null);
    }
}
