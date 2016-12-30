package com.inz.praca.integration.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.math.BigDecimal;

import com.inz.praca.builders.ProductBuilder;
import com.inz.praca.domain.Product;
import com.inz.praca.integration.IntegrationTestBase;
import com.inz.praca.service.ProductService;
import org.junit.Test;

import org.springframework.beans.factory.annotation.Autowired;

public class ProductControllerTest extends IntegrationTestBase {

	@Autowired
	ProductService productService;

	@Test
	public void should_show_newProduct_page() throws Exception {
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

	@Test
	public void shouldShowProductDetailsPage() throws Exception {
		Product product = productService.createProduct(new ProductBuilder().withName("name").withPrice(BigDecimal.TEN).createProductDTO());
		mvc.perform(get("/products/product/" + product.getId())
				.param("name","name")
				.param("imageUrl","url")
				.param("description","desc")
				.param("price","10"))
				.andExpect(status().isOk())
				.andExpect(view().name("product"));
	}

}
