package com.school.app.controllers;

import com.school.app.model.User;
import com.school.app.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/app")
@RequiredArgsConstructor
public class StudentController {


    private final UserRepository userRepository;

    @GetMapping("/v1/user-detail")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<User> getStudentDetailByMail(@RequestParam String email) {
        User user=userRepository.findByEmail(email).orElse(null);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/v1/all-user-detail")
    public ResponseEntity<List<User>> getAllStudentDetailByMail() {

        return ResponseEntity.status(HttpStatus.ACCEPTED).body
                (StreamSupport.stream(userRepository.findAll().spliterator(),true)
                        .collect(toList()));
    }
}
