package com.inz.praca.builders;

import com.inz.praca.domain.User;
import com.inz.praca.dto.UserDTO;

public class UserBuilder {
	private String email;
	private String name = "imie";
	private String lastName = "nazwisko";
	private String passwordHash;

	public UserBuilder withEmail(String email) {
		this.email = email;
		return this;
	}

	public UserBuilder withName(String name) {
		this.name = name;
		return this;
	}

	public UserBuilder withLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}

	public UserBuilder withPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
		return this;
	}

	public User build() {
		return new User(email, name, lastName, passwordHash);
	}

	public User build(UserDTO userDTO) {
		return new User(userDTO.getEmail(), userDTO.getName(), userDTO.getLastName(), userDTO.getPassword());
	}
}