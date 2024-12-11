package com.gyro.model;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

public class User {
	
	
	@NotEmpty(message = "{NotEmpty.username}")
    private String username;


	@NotEmpty(message = "{NotEmpty.password}")
	@Size(max = 4, min = 4, message = "{Size.password}")
    private String password;


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
