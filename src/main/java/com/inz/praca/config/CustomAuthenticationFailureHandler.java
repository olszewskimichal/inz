package com.inz.praca.config;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;


@Component
@Slf4j
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	@Autowired
	private MessageSource messages;

	@Autowired
	private LocaleResolver localeResolver;

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
			throws IOException, ServletException {
		log.info("Bledna autoryzacja uzytkownika");
		log.info(exception.getMessage());
		setDefaultFailureUrl("/login-error");
		super.onAuthenticationFailure(request, response, exception);
		Locale locale = localeResolver.resolveLocale(request);
		String errorMessage = messages.getMessage("login.error_message", null, locale);

		if ("user is disabled".equalsIgnoreCase(exception.getMessage())) {
			errorMessage = messages.getMessage("auth.message.disabled", null, locale);
		}
		request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, errorMessage);
	}

}
