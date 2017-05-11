package com.inz.praca.units.controller;

import com.inz.praca.UnitTest;
import com.inz.praca.registration.User;
import com.inz.praca.registration.UserService;
import com.inz.praca.registration.UsersController;
import com.inz.praca.utils.Pager;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

@Category(UnitTest.class)
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
        assertThat(usersController.getUsersPage(model, 1)).isEqualTo("users");
        verify(model).addAttribute("Users", users);
        verify(model).addAttribute("selectedPageSize", 0);
        verify(model).addAttribute(Matchers.eq("pager"), Matchers.any(Pager.class));
        verifyNoMoreInteractions(model);
    }

    @Test
    public void shouldReturnUsersPageWhenNullArgument() {
        Page<User> users = new PageImpl<>(new ArrayList<>());
        given(userService.getAllUsers(0)).willReturn(users);
        assertThat(usersController.getUsersPage(model, null)).isEqualTo("users");
        verify(model).addAttribute("Users", users);
        verify(model).addAttribute("selectedPageSize", 0);
        verify(model).addAttribute(Matchers.eq("pager"), Matchers.any(Pager.class));
        verifyNoMoreInteractions(model);
    }

    @Test
    public void shouldActivateUserAndRedirectToUsers() {
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
        given(userService.changeUserActive(1L, true)).willReturn("msg");
        assertThat(usersController.activateUser(1L, true, redirectAttributes)).isEqualTo("redirect:/users");
        verify(redirectAttributes).addFlashAttribute("activate", true);
        verify(redirectAttributes).addFlashAttribute("activateMessage", "msg");
    }
}
