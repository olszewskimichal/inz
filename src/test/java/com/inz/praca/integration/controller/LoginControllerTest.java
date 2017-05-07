package com.inz.praca.integration.controller;

import com.inz.praca.integration.IntegrationTestBase;
import com.inz.praca.registration.UserBuilder;
import com.inz.praca.registration.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ActiveProfiles(value = "development")
public class LoginControllerTest extends IntegrationTestBase {
	@Autowired
	private WebApplicationContext context;

	@Autowired
	private UserRepository userRepository;

	private MockMvc mvc;


	@Before
	public void setUp() throws Exception {
		mvc = MockMvcBuilders.webAppContextSetup(context)
				.apply(springSecurity())
				.build();
	}

	@Test
	public void shouldLoginWithCorrectLoginAndPasswordAndActiveUser() throws Exception {
		//given
		userRepository.save(new UserBuilder().withEmail("emailTest@o2.pl").withPasswordHash("zaq1@WSX").activate().build());
		String userLogin = "emailTest@o2.pl";
		String password = "zaq1@WSX";
		//when
		RequestBuilder requestBuilder = post("/login")
				.param("username", userLogin)
				.param("password", password);
		//then
		mvc.perform(requestBuilder)
				.andDo(print())
				.andExpect(redirectedUrl("/"))
				.andExpect(authenticated().withUsername("emailTest@o2.pl"));
	}

	@Test
	public void shouldFailLoginAndRedirect() throws Exception {
		//given
		String userLogin = "admin";
		String password = "incorrectPassword";
		//when
		RequestBuilder requestBuilder = formLogin("/login")
				.user("username", userLogin)
				.password("password", password);
		//then
		mvc.perform(requestBuilder)
				.andDo(print())
				.andExpect(redirectedUrl("/login-error"))
				.andExpect(unauthenticated());
	}

	@Test
	public void shouldReturnErrorMessage() throws Exception {
		//when
		mvc.perform(get("/login-error"))
				.andDo(print())
				//then
				.andExpect(model().attribute("loginError", true))
				.andExpect(status().isOk())
				.andExpect(content().string(
						allOf(
								containsString("<div class=\"alert alert-danger\" role=\"alert\">")
						))
				)
				.andExpect(view().name("login"));
	}

}


