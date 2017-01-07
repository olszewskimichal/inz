package com.inz.praca.integration.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.inz.praca.dto.CartSession;
import com.inz.praca.integration.IntegrationTestBase;
import org.junit.Test;

import org.springframework.beans.factory.annotation.Autowired;

public class OrderControllerTest extends IntegrationTestBase {

	@Autowired
	CartSession cartSession;

	@Test
	public void shouldShowShippingDetailPage() throws Exception {
		mvc.perform(get("/order"))
				.andExpect(view().name("collectCustomerInfo"));
	}

}
