package com.inz.praca.units.controller;

import com.inz.praca.login.LoginController;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.web.WebAttributes;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.MockitoAnnotations.initMocks;

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
        HttpServletRequest request = new MockHttpServletRequest();
        HttpSession session = request.getSession();
        session.setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, "Nieprawidłowy użytkownik");
        assertThat(controller.loginError(model, request)).isEqualTo("login");
        verify(model).addAttribute("loginError", true);
        verify(model).addAttribute("errorMessage", "Nieprawidłowy użytkownik");
        verifyNoMoreInteractions(model);
    }

}
