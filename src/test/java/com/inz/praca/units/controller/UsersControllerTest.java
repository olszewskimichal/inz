package com.inz.praca.units.controller;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.ArrayList;

import com.inz.praca.controller.UsersController;
import com.inz.praca.domain.User;
import com.inz.praca.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public class UsersControllerTest {

	@Mock
	private Model model;

	@Mock
	private UserService userService;

	private UsersController usersController;

	@Before
	public void setUp() {
		initMocks(this);
		usersController = new UsersController(userService);
	}

	@Test
	public void shouldReturnUsersPage() {
		Page<User> users = new PageImpl<>(new ArrayList<>());
		given(userService.getAllUsers(0)).willReturn(users);
		assertThat(usersController.getUsersPage(model)).isEqualTo("users");
		verify(model).addAttribute("Users", users);
		verifyNoMoreInteractions(model);
	}

	@Test
	public void shouldActivateUserAndRedirectToUsers() {
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		given(userService.changeUserActive(1L)).willReturn("msg");
		assertThat(usersController.activateUser(1L, redirectAttributes)).isEqualTo("redirect:/users");
		verify(redirectAttributes).addFlashAttribute("activate", true);
		verify(redirectAttributes).addFlashAttribute("activateMessage", "msg");
	}
}
