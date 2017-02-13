package com.inz.praca.controller;

import com.inz.praca.service.UserService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Slf4j
public class UsersController {
	private final UserService userService;

	@Autowired
	public UsersController(UserService userService) {
		this.userService = userService;
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping("/users")
	public String getUsersPage(Model model) {
		log.info("Pobranie wszystkich uzytkownikow");
		model.addAttribute("Users", userService.getAllUsers(0));
		return "users";
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value = "/user/active/{id}", method = RequestMethod.GET)
	public String activateUser(@PathVariable final Long id, RedirectAttributes redirectAttributes) {
		log.debug("Aktywacja uzytkownika o id {}", id);
		String message = userService.changeUserActive(id);
		redirectAttributes.addFlashAttribute("activate", true);
		redirectAttributes.addFlashAttribute("activateMessage", message);
		return "redirect:/users";
	}

}
