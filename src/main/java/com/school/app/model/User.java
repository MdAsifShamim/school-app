package com.school.app.model;

import java.time.LocalDateTime;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long userId;

	@Column(name = "name")
	private String name;

	@Column(name = "email")
	private String email;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@Column(name = "pwd")
	private String pwd;

	@Column(name = "mobile_number")
	private String mobileNumber;

	@Column(name = "role")
	private String role;

	@JsonIgnore
	@Column(name = "create_dt")
	private LocalDateTime createDt;

	@JsonIgnore
	@Column(name = "update_dt")
	private LocalDateTime updateDt;

	@OneToMany(mappedBy = "user" ,fetch = FetchType.EAGER)
	@JsonIgnore
	private Set<Authority> authorities;

}
