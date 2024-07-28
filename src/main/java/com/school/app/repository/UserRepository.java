package com.school.app.repository;


import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.school.app.model.User;

public interface UserRepository extends CrudRepository<User, Long> {

	Optional<User> findByEmail(String email);

}
