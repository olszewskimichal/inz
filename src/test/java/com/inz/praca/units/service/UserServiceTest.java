package com.inz.praca.units.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;

import java.util.Arrays;
import java.util.Optional;

import com.inz.praca.registration.User;
import com.inz.praca.registration.UserBuilder;
import com.inz.praca.registration.UserDTO;
import com.inz.praca.registration.UserNotFoundException;
import com.inz.praca.registration.UserRepository;
import com.inz.praca.registration.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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
		given(userRepository.findById(1L)).willReturn(Optional.of(new UserBuilder().withEmail("email@o2.pl").withPasswordHash("zaq1@WSX").build()));
		User userById = this.userService.getUserById(1L);
		assertThat(userById.getEmail()).isEqualTo("email@o2.pl");

	}

	@Test
	public void shouldFoundUserByEmail() {
		given(userRepository.findByEmail("email@o2.pl")).willReturn(Optional.of(new UserBuilder().withEmail("email@o2.pl").withPasswordHash("zaq1@WSX").build()));
		User userByEmail = this.userService.getUserByEmail("email@o2.pl");
		assertThat(userByEmail.getEmail()).isEqualTo("email@o2.pl");
	}


	@Test
	public void shouldCreateUserWhenFormIsCorrect() {
		UserDTO userDTO = new UserDTO(); userDTO.setConfirmPassword("zaq1@WSX");
		userDTO.setPassword("zaq1@WSX"); userDTO.setEmail("email@o2.pl");
		userDTO.setName("name"); userDTO.setLastName("last");
		doAnswer(invocation -> invocation.getArguments()[0]).when(userRepository).save(any(User.class));

		User user = userService.createUserFromDTO(userDTO);
		assertThat(user.getEmail()).isEqualTo(userDTO.getEmail());
		assertThat(user.getName()).isEqualTo(userDTO.getName());
		assertThat(user.getLastName()).isEqualTo(userDTO.getLastName());
		assertThat(new BCryptPasswordEncoder().matches(userDTO.getPassword(), user.getPasswordHash())).isTrue();
	}

	@Test
	public void shouldGetAllUsers() {
		Page<User> users = new PageImpl<>(Arrays.asList(new UserBuilder().withEmail("name3@o2.pl").withPasswordHash("zaq1@WSX").build(), new UserBuilder().withEmail("name4@o2.pl").withPasswordHash("zaq1@WSX").build()));
		given(this.userRepository.findAll(new PageRequest(0, 20))).willReturn(users);
		Page<User> allUsers = userService.getAllUsers(0);
		assertThat(allUsers).isNotEmpty();
		assertThat(allUsers.getTotalElements()).isEqualTo(2);
	}

	@Test
	public void shouldActivateUser() {
		doAnswer(invocation -> invocation.getArguments()[0]).when(userRepository).save(any(User.class));
		given(userRepository.findById(1L)).willReturn(Optional.ofNullable(new UserBuilder().withEmail("name3@o2.pl").withPasswordHash("zaq1@WSX").build()));
		String result = userService.changeUserActive(1L, true);
		assertThat(result).isEqualTo("Aktywowano uzytkownika name3@o2.pl");
		result = userService.changeUserActive(1L, false);
		assertThat(result).isEqualTo("Deaktywowano uzytkownika name3@o2.pl");
	}
}
