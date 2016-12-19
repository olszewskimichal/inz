package com.inz.praca.integration.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.inz.praca.integration.IntegrationTestBase;
import org.junit.Before;
import org.junit.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

public class RegisterControllerTest extends IntegrationTestBase {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mvc;


	@Before
	public void setUp() throws Exception {
		this.mvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void should_show_register_page() throws Exception {
		mvc.perform(get("/register"))
				.andExpect(status().isOk())
				.andExpect(view().name("register"));
	}

	@Test
	public void should_process_registration() throws Exception {
		mvc.perform(post("/register")
				.param("name", "adam")
				.param("lastName", "malysz")
				.param("email", "a1@o2.pl")
				.param("password", "zaq1@WSX")
				.param("confirmPassword", "zaq1@WSX"))
				.andExpect(model().errorCount(0));
	}

	@Test
	public void should_failed_registration() throws Exception {
		mvc.perform(post("/register"))
				.andExpect(status().isOk())
				.andExpect(view().name("register"))
				.andExpect(model().errorCount(3));
	}

	@Test
	public void should_failed_with_easy_password() throws Exception {
		mvc.perform(post("/register")
				.param("name", "adam")
				.param("lastName", "malysz")
				.param("email", "a3@o2.pl")
				.param("password", "11111111")
				.param("confirmPassword", "11111111"))
				.andExpect(status().isOk())
				.andExpect(view().name("register"))
				.andExpect(model().errorCount(1));
	}

}
