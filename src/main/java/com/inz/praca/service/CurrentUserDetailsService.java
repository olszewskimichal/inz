package com.inz.praca.service;

import com.inz.praca.dto.CurrentUser;
import com.inz.praca.domain.User;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@Profile("development")
public class CurrentUserDetailsService implements UserDetailsService {
	private final UserService userService;

	@Autowired
	public CurrentUserDetailsService(UserService userService) {
		this.userService = userService;
	}

	@Override
	public CurrentUser loadUserByUsername(String value) {
		log.info("Autentykacja uzytkownika {}", value);
		User user = userService.getUserByEmail(value);
		return new CurrentUser(user);
	}

}