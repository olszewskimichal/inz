package com.inz.praca.units.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;

import java.util.Optional;

import com.inz.praca.domain.User;
import com.inz.praca.domain.UserBuilder;
import com.inz.praca.dto.UserDTO;
import com.inz.praca.exceptions.UserNotFoundException;
import com.inz.praca.repository.UserRepository;
import com.inz.praca.service.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class UserServiceTest {

	@Mock
	private UserRepository userRepository;

	private UserService userService;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		this.userService = new UserService(userRepository);
	}

	@Test
	public void shouldThrownIlleagalArgumentExceptionWhenEmailIsNull() {
		try {
			this.userService.getUserByEmail(null);
			Assert.fail("Nie mozna podawac nullowego argumentu przy szukaniu uzytkownika");
		}
		catch (IllegalArgumentException e) {
			assertThat(e.getMessage()).isEqualTo("Nie podano adresu email");
		}
	}

	@Test
	public void shouldThrownIlleagalArgumentExceptionWhenEmailIsEmpty() {
		try {
			this.userService.getUserByEmail("");
			Assert.fail("Nie mozna podawac pustego argumentu przy szukaniu uzytkownika");
		}
		catch (IllegalArgumentException e) {
			assertThat(e.getMessage()).isEqualTo("Podano pusty email");
		}
	}

	@Test
	public void shouldThrownIlleagalArgumentExceptionWhenIdIsNull() {
		try {
			this.userService.getUserById(null);
			Assert.fail("Nie mozna podawac nullowego argumentu przy szukaniu uzytkownika");
		}
		catch (IllegalArgumentException e) {
			assertThat(e.getMessage()).isEqualTo("Podano puste id uzytkownika");
		}
	}

	@Test
	public void shouldThrownIlleagalArgumentExceptionWhenIdIsNotPositive() {
		try {
			this.userService.getUserById(0L);
			Assert.fail("Nie mozna podawac mniejszego od 1 argumentu id przy szukaniu uzytkownika");
		}
		catch (IllegalArgumentException e) {
			assertThat(e.getMessage()).isEqualTo("Nie ma uzytkownikow o id mniejszym niz 1");
		}
	}

	@Test
	public void shouldThrownUserNotFoundExceptionWhenUserByEmailNotExists() {
		try {
			given(userRepository.findByEmail(anyString())).willReturn(Optional.empty());
			this.userService.getUserByEmail("email");
			Assert.fail("Nie istnieje uzytkownik o podanym mailu");
		}
		catch (UserNotFoundException e) {
			assertThat(e.getMessage()).isEqualTo("Nie znaleziono uzytkownika o emailu = email");
		}
	}

	@Test
	public void shouldThrownUserNotFoundExceptionWhenUserByIdNotExists() {
		try {
			given(userRepository.findById(anyLong())).willReturn(Optional.empty());
			this.userService.getUserById(1L);
			Assert.fail("Nie istnieje uzytkownik o podanym mailu");
		}
		catch (UserNotFoundException e) {
			assertThat(e.getMessage()).isEqualTo("Nie znaleziono uzytkownika o id = 1");
		}
	}

	@Test
	public void shouldFoundUserById() {
		given(userRepository.findById(1L)).willReturn(Optional.of(new UserBuilder().withEmail("email@o2.pl").withPasswordHash("hash").build()));
		User userById = this.userService.getUserById(1L);
		assertThat(userById.getEmail()).isEqualTo("email@o2.pl");

	}

	@Test
	public void shouldFoundUserByEmail() {
		given(userRepository.findByEmail("email@o2.pl")).willReturn(Optional.of(new UserBuilder().withEmail("email@o2.pl").withPasswordHash("hash").build()));
		User userByEmail = this.userService.getUserByEmail("email@o2.pl");
		assertThat(userByEmail.getEmail()).isEqualTo("email@o2.pl");
	}


	@Test
	public void shouldCreateUserWhenFormIsCorrect() {
		UserDTO userDTO = new UserDTO();
		userDTO.setConfirmPassword("psx");
		userDTO.setPassword("psx");
		userDTO.setEmail("email@o2.pl");
		userDTO.setName("name");
		userDTO.setLastName("last");

		doAnswer(invocation -> {
			User argument = (User) invocation.getArguments()[0];
			argument.setId(1L);
			return argument;
		}).when(userRepository).save(any(User.class));

		User user = userService.create(userDTO);
		assertThat(user.getEmail()).isEqualTo(userDTO.getEmail());
		assertThat(user.getName()).isEqualTo(userDTO.getName());
		assertThat(user.getLastName()).isEqualTo(userDTO.getLastName());
		assertThat(user.getPasswordHash()).isEqualTo(userDTO.getPassword());
		assertThat(user.getId()).isEqualTo(1L);
	}
}
