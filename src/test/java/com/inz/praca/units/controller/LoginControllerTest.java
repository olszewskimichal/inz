package com.inz.praca.units.controller;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.Optional;

import com.inz.praca.controller.LoginController;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import org.springframework.ui.Model;

public class LoginControllerTest {

	@Mock
	private Model model;

	private LoginController controller;

	@Before
	public void setUp() {
		initMocks(this);
		controller = new LoginController();
	}

	@Test
	public void shouldReturnLoginPage() {
		assertThat(controller.loginPage(model, Optional.empty())).isEqualTo("login");
	}

	@Test
	public void shouldReturnErrorLoginPage() {
		assertThat(controller.loginError(model)).isEqualTo("login");
		verify(model).addAttribute("loginError", true);
		verifyNoMoreInteractions(model);
	}


}
