package com.inz.praca.units.controller;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;

import com.inz.praca.controller.RegisterController;
import com.inz.praca.dto.UserDTO;
import com.inz.praca.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

public class RegisterControllerTests {

	@Mock
	private Model model;

	@Mock
	private UserService userService;

	@Mock
	private BindingResult bindingResult;

	private RegisterController registerController;

	@Before
	public void setUp() {
		initMocks(this);
		registerController = new RegisterController(userService);
	}

	@Test
	public void shouldReturnRegisterPage() {
		assertThat(registerController.registerPage(model)).isEqualTo("register");
	}

	@Test
	public void shouldCreateUserAndRedirectToIndex() {
		UserDTO userDTO = new UserDTO();
		userDTO.setConfirmPassword("psx");
		userDTO.setPassword("psx");
		userDTO.setEmail("email");
		userDTO.setName("name");
		userDTO.setLastName("last");
		assertThat(registerController.confirmRegistration(userDTO, bindingResult, model)).isEqualTo("index");
	}

}
