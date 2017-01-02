package com.inz.praca.integration.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.math.BigDecimal;

import com.inz.praca.builders.ProductBuilder;
import com.inz.praca.domain.Product;
import com.inz.praca.integration.IntegrationTestBase;
import com.inz.praca.service.ProductService;
import org.junit.Test;

import org.springframework.beans.factory.annotation.Autowired;

public class CartControllerTest extends IntegrationTestBase {

	@Autowired
	ProductService productService;

	@Test
	public void shouldShowCartPage() throws Exception {
		mvc.perform(get("/cart"))
				.andExpect(view().name("cart"));
	}

	@Test
	public void shouldRedirectAfterAddProductToCart() throws Exception {
		Product product = productService.createProduct(new ProductBuilder().withName("aaa").withPrice(BigDecimal.TEN).withCategory("inne").createProductDTO());
		mvc.perform(get("/cart/add/" + product.getId()))
				.andExpect(redirectedUrl("/cart"));
	}

	@Test
	public void shouldRedirectAfterRemoveProductFromCart() throws Exception {
		mvc.perform(get("/cart/remove/" + 0))
				.andExpect(redirectedUrl("/cart"));
	}

	@Test
	public void shouldRedirectAfterClearCart() throws Exception {
		mvc.perform(get("/cart/clear"))
				.andExpect(redirectedUrl("/cart"));
	}

}
