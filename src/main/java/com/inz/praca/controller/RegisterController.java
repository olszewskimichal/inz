package com.inz.praca.controller;

import javax.validation.Valid;

import com.inz.praca.dto.UserDTO;
import com.inz.praca.service.UserService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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
	public String confirmRegistration(@Valid @ModelAttribute(FORM) UserDTO userCreateForm, BindingResult errors, Model model) {
		log.info("próba rejestracji {}", userCreateForm);
		if (errors.hasErrors()) {
			log.info("wystapil blad {} podczas validacji uzytkownika {}", errors.getAllErrors().toString(), userCreateForm);
			model.addAttribute(FORM, userCreateForm);
			return REGISTER;
		}
		try {
			userService.create(userCreateForm);
			return "index";
		}
		catch (IllegalArgumentException e) {
			log.debug(e.getMessage());
			model.addAttribute(FORM, userCreateForm);
		}
		return REGISTER;
	}
}
