package com.inz.praca.integration.service;

import static org.assertj.core.api.Java6Assertions.assertThat;

import com.inz.praca.domain.User;
import com.inz.praca.integration.IntegrationTestBase;
import com.inz.praca.repository.UserRepository;
import com.inz.praca.service.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.springframework.beans.factory.annotation.Autowired;


public class UserServiceTest extends IntegrationTestBase {

	@Autowired
	UserService userService;

	@Autowired
	protected UserRepository repository;

	@After
	public void tearUP() {
		repository.deleteAll();
	}

	private Long id;

	@Before
	public void setUp() {
		for (int i = 0; i < 5; i++) {
			id = repository.save(new User("email" + i, "imie", "nazwisko", "hash")).getId();
		}
	}

	@Test
	public void shouldFindUserByEmail() {
		User userByEmail = userService.getUserByEmail("email1");
		assertThat(userByEmail).isNotNull();
		assertThat(userByEmail.getEmail()).isEqualTo("email1");
		assertThat(userByEmail.getId()).isNotNull();
	}

	@Test
	public void shouldFindUserById() {
		User userById = userService.getUserById(id);
		assertThat(userById).isNotNull();
		assertThat(userById.getId()).isNotNull();
	}

}
