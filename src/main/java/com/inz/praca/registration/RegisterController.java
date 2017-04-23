package com.inz.praca.registration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@Slf4j
public class RegisterController {

    private static final String REGISTER = "register";
    private static final String FORM = "userCreateForm";
    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("userCreateForm", new UserDTO());
        return REGISTER;
    }

    @PostMapping(value = "/register")
    public String confirmRegistration(@Valid @ModelAttribute(FORM) UserDTO userCreateForm, BindingResult errors, Model model, RedirectAttributes redirectAttributes) {
        log.info("próba rejestracji {}", userCreateForm);
        if (errors.hasErrors()) {
            log.info("wystapil blad {} podczas validacji uzytkownika {}", errors.getAllErrors().toString(),
                    userCreateForm);
            model.addAttribute(FORM, userCreateForm);
            return REGISTER;
        }
        try {
            userService.createUserFromDTO(userCreateForm);
            redirectAttributes.addFlashAttribute("registerDone", true);
            return "redirect:/login";
        } catch (IllegalArgumentException e) {
            log.debug(e.getMessage());
            model.addAttribute(FORM, userCreateForm);
        } catch (DataIntegrityViolationException ex) {
            log.debug(ex.getMessage());
            model.addAttribute(FORM, userCreateForm);
            model.addAttribute("uzytkownikIstnieje", true);
        }
        return REGISTER;
    }

}
