package com.inz.praca.service;

import com.inz.praca.domain.User;
import com.inz.praca.exceptions.UserNotFoundException;
import com.inz.praca.repository.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class UserService {
	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public User getUserById(Long id) {
		Assert.notNull(id, "Podano puste id uzytkownika");
		Assert.isTrue(id > 0L, "Nie ma uzytkownikow o id mniejszym niz 1");
		return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
	}

	public User getUserByEmail(String email) {
		Assert.notNull(email, "Nie podano adresu email");
		Assert.hasLength(email, "Podano pusty email");
		return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));
	}

}
