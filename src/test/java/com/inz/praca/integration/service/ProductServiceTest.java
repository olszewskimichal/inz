package com.inz.praca.integration.service;

import static org.assertj.core.api.Java6Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;

import com.inz.praca.domain.Product;
import com.inz.praca.integration.IntegrationTestBase;
import com.inz.praca.repository.ProductRepository;
import com.inz.praca.service.ProductService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.springframework.beans.factory.annotation.Autowired;

public class ProductServiceTest extends IntegrationTestBase {

	@Autowired
	ProductService productService;

	@Autowired
	ProductRepository repository;

	@Before
	public void setUp() {
		for (int i = 0; i < 30; i++) {
			repository.save(new Product("nazwa" + i, "opis", "url", BigDecimal.ZERO));
		}

	}

	@After
	public void tearUp() {
		repository.deleteAll();
	}

	@Test
	public void shouldReturn20ProductAscSortByIdWhenSizeArgumentIsEqualTo30() {
		List<Product> products = this.productService.getProducts(1, 30, "desc");
		assertThat(products.size()).isEqualTo(20); //taki limit ustalony w ProductService
		Long firstId = products.get(0).getId();
		Long lastId = products.get(products.size() - 1).getId();
		assertThat(firstId > lastId).isTrue();
		assertThat(firstId - lastId).isEqualTo(19L);
	}
}
