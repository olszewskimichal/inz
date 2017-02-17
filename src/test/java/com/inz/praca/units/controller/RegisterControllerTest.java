package com.inz.praca.units.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.MockitoAnnotations.initMocks;

import com.inz.praca.controller.RegisterController;
import com.inz.praca.dto.UserDTO;
import com.inz.praca.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public class RegisterControllerTest {

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
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		UserDTO userDTO = new UserDTO();
		userDTO.setConfirmPassword("psx");
		userDTO.setPassword("psx");
		userDTO.setEmail("email");
		userDTO.setName("name");
		userDTO.setLastName("last");
		assertThat(registerController.confirmRegistration(userDTO, bindingResult, model, redirectAttributes)).isEqualTo("redirect:/login");
		verify(redirectAttributes).addFlashAttribute("registerDone", true);
	}

	@Test
	public void shouldFailedRegisterWithNotCorrectUser() {
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		given(userService.create(any(UserDTO.class))).willThrow(new IllegalArgumentException());

		UserDTO userDTO = new UserDTO();
		userDTO.setName("imie");
		userDTO.setLastName("nazw");
		userDTO.setEmail("email");
		userDTO.setPassword("pass");
		assertThat(registerController.confirmRegistration(userDTO, bindingResult, model, redirectAttributes)).isEqualTo("register");

		verify(model).addAttribute("userCreateForm", userDTO);
		verifyNoMoreInteractions(model);
	}

	@Test
	public void shouldFailedRegisterWithExistingUser() {
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		given(userService.create(any(UserDTO.class))).willThrow(new DataIntegrityViolationException("msg"));

		UserDTO userDTO = new UserDTO();
		userDTO.setName("imie");
		userDTO.setLastName("nazw");
		userDTO.setEmail("email");
		userDTO.setPassword("pass");
		assertThat(registerController.confirmRegistration(userDTO, bindingResult, model, redirectAttributes)).isEqualTo("register");

		verify(model).addAttribute("userCreateForm", userDTO);
		verify(model).addAttribute("uzytkownikIstnieje", true);
		verifyNoMoreInteractions(model);
	}

	@Test
	public void shouldShowAgainFormWhenErrorOnCreate() {
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		given(bindingResult.hasErrors()).willReturn(true);

		UserDTO userDTO = new UserDTO();
		assertThat(registerController.confirmRegistration(userDTO, bindingResult, model, redirectAttributes)).isEqualTo("register");

		verify(model).addAttribute("userCreateForm", userDTO);
		verifyNoMoreInteractions(model);
	}

}
