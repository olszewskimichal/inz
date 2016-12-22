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

public class ProductControllerTest extends IntegrationTestBase {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mvc;

	@Before
	public void setUp() throws Exception {
		this.mvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void should_show_register_page() throws Exception {
		mvc.perform(get("/addProduct"))
				.andExpect(status().isOk())
				.andExpect(view().name("newProduct"));
	}

	@Test
	public void should_process_new_product_create() throws Exception {
		mvc.perform(post("/addProduct")
				.param("name", "nazwa")
				.param("imageUrl", "http://localhost")
				.param("description", "opis")
				.param("price", "1.0"))
				.andExpect(model().errorCount(0));
	}

	@Test
	public void should_failed_addProduct() throws Exception {
		mvc.perform(post("/addProduct"))
				.andExpect(status().isOk())
				.andExpect(view().name("newProduct"))
				.andExpect(model().errorCount(2));
	}

	@Test
	public void should_failed_withNotCorrectPrice() throws Exception {
		mvc.perform(post("/addProduct")
				.param("name", "nazwa")
				.param("imageUrl", "http://localhost")
				.param("description", "opis")
				.param("price", "a1.0"))
				.andExpect(model().errorCount(1));
	}

}
