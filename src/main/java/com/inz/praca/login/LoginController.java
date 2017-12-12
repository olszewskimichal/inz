package com.inz.praca.login;

import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

  private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

  @RequestMapping("/login-error")
  public String loginError(Model model, HttpServletRequest request) {
    logger.info("Błedne logowanie");
    model.addAttribute("loginError", true);
    model.addAttribute("errorMessage", request.getSession().getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION));
    return "login";
  }

  @RequestMapping("/login")
  public String loginPage(Model model, @RequestParam Optional<String> error) {
    model.addAttribute("error", error);
    logger.info("Logowanie");
    return "login";
  }
}
