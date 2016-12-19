package com.inz.praca.integration.service;

import static org.assertj.core.api.Java6Assertions.assertThat;

import com.inz.praca.domain.User;
import com.inz.praca.dto.UserDTO;
import com.inz.praca.integration.IntegrationTestBase;
import com.inz.praca.repository.UserRepository;
import com.inz.praca.service.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;


public class UserServiceTest extends IntegrationTestBase {

	@Autowired
	UserService userService;

	@Autowired
	protected UserRepository repository;

	@Before
	public void setUp() {
		repository.deleteAll();
	}


	@Test
	public void shouldFindUserByEmail() {
		repository.save(new User("email" + 1, "imie", "nazwisko", "hash")).getId();
		User userByEmail = userService.getUserByEmail("email1");
		assertThat(userByEmail).isNotNull();
		assertThat(userByEmail.getEmail()).isEqualTo("email1");
		assertThat(userByEmail.getId()).isNotNull();
	}

	@Test
	public void shouldFindUserById() {
		Long id = repository.save(new User("email", "imie", "nazwisko", "hash")).getId();
		User userById = userService.getUserById(id);
		assertThat(userById).isNotNull();
		assertThat(userById.getId()).isNotNull();
	}

	@Test
	public void shouldCreateUserWhenDtoIsCorrect() {
		assertThat(repository.findAll().size()).isEqualTo(0);

		//given
		UserDTO userDTO = new UserDTO();
		userDTO.setName("name");   //UserDTO ->Builder
		userDTO.setLastName("lastName");
		userDTO.setEmail("email");
		userDTO.setPassword("aaa");

		//when
		User user = userService.create(userDTO);

		//then
		assertThat(repository.findAll().size()).isEqualTo(1);
		assertThat(user.getEmail()).isEqualTo("email");
		assertThat(user.getPasswordHash()).isEqualTo("aaa");
	}

	@Test
	public void shouldCreateUserWhenDtoIsCorrectButWhenAgainThenFailed() {
		assertThat(repository.findAll().size()).isEqualTo(0);

		//given
		UserDTO userDTO = new UserDTO();
		userDTO.setName("name");   //UserDTO ->Builder
		userDTO.setLastName("lastName");
		userDTO.setEmail("email");
		userDTO.setPassword("aaa");

		//when
		User user = userService.create(userDTO);

		//then
		assertThat(repository.findAll().size()).isEqualTo(1);
		assertThat(user.getEmail()).isEqualTo("email");
		assertThat(user.getPasswordHash()).isEqualTo("aaa");

		try {
			userService.create(userDTO);
			Assert.fail();
		}
		catch (DataIntegrityViolationException e) {
			assertThat(repository.findAll().size()).isEqualTo(1);
		}
	}

}
