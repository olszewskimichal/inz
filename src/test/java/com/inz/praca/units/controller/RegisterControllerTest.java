package com.inz.praca.units.controller;

import com.inz.praca.UnitTest;
import com.inz.praca.registration.RegisterController;
import com.inz.praca.registration.UserDTO;
import com.inz.praca.registration.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

@Category(UnitTest.class)
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
        this.registerController = new RegisterController(this.userService);
    }

    @Test
    public void shouldReturnRegisterPage() {
        assertThat(this.registerController.registerPage(this.model)).isEqualTo("register");
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
        assertThat(this.registerController.confirmRegistration(userDTO, this.bindingResult, this.model, redirectAttributes)).isEqualTo(
                "redirect:/login");
        verify(redirectAttributes).addFlashAttribute("registerDone", true);
    }

    @Test
    public void shouldFailedRegisterWithNotCorrectUser() {
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
        given(this.userService.createUserFromDTO(any(UserDTO.class))).willThrow(new IllegalArgumentException());

        UserDTO userDTO = new UserDTO();
        userDTO.setName("imie");
        userDTO.setLastName("nazw");
        userDTO.setEmail("email");
        userDTO.setPassword("pass");
        assertThat(this.registerController.confirmRegistration(userDTO, this.bindingResult, this.model, redirectAttributes)).isEqualTo(
                "register");

        verify(this.model).addAttribute("userCreateForm", userDTO);
        verifyNoMoreInteractions(this.model);
    }

    @Test
    public void shouldFailedRegisterWithExistingUser() {
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
        given(this.userService.createUserFromDTO(any(UserDTO.class))).willThrow(new DataIntegrityViolationException("msg"));

        UserDTO userDTO = new UserDTO();
        userDTO.setName("imie");
        userDTO.setLastName("nazw");
        userDTO.setEmail("email");
        userDTO.setPassword("pass");
        assertThat(this.registerController.confirmRegistration(userDTO, this.bindingResult, this.model, redirectAttributes)).isEqualTo(
                "register");

        verify(this.model).addAttribute("userCreateForm", userDTO);
        verify(this.model).addAttribute("uzytkownikIstnieje", true);
        verifyNoMoreInteractions(this.model);
    }

    @Test
    public void shouldShowAgainFormWhenErrorOnCreate() {
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
        given(this.bindingResult.hasErrors()).willReturn(true);

        UserDTO userDTO = new UserDTO();
        assertThat(this.registerController.confirmRegistration(userDTO, this.bindingResult, this.model, redirectAttributes)).isEqualTo(
                "register");

        verify(this.model).addAttribute("userCreateForm", userDTO);
        verifyNoMoreInteractions(this.model);
    }

}
