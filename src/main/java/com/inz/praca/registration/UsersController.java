package com.inz.praca.registration;

import com.inz.praca.utils.Pager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Slf4j
public class UsersController {
    private static final int INITIAL_PAGE = 0;
    private static final int BUTTONS_TO_SHOW = 5;
    private final UserService userService;

    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping("/users")
    public String getUsersPage(Model model, @RequestParam(value = "page", required = false) Integer page) {
        log.info("Pobranie wszystkich uzytkownikow");
        int evalPage = page == null ? INITIAL_PAGE : page - 1;
        Page<User> users = userService.getAllUsers(evalPage);
        if (users != null) {
            model.addAttribute("Users", users.getContent());
            model.addAttribute("selectedPageSize", evalPage);
            model.addAttribute("pager", new Pager(users.getTotalPages(), users.getNumber(), BUTTONS_TO_SHOW));
        }
        return "users";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/user/active/{activity}/{userId}", method = RequestMethod.GET)
    public String activateUser(@PathVariable final Long userId, @PathVariable final Boolean activity, RedirectAttributes redirectAttributes) {
        log.debug("Aktywacja uzytkownika o userId {}", userId);
        String message = userService.changeUserActive(userId, activity);
        redirectAttributes.addFlashAttribute("activate", true);
        redirectAttributes.addFlashAttribute("activateMessage", message);
        return "redirect:/users";
    }

}
