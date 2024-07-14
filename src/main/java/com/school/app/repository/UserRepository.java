package com.school.app.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.school.app.model.User;

public interface UserRepository extends CrudRepository<User, Long> {

	List<User> findByEmail(String email);

}
