package com.inz.praca.integration.service;

import static org.assertj.core.api.Java6Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;

import com.inz.praca.domain.Product;
import com.inz.praca.builders.ProductBuilder;
import com.inz.praca.dto.ProductDTO;
import com.inz.praca.integration.IntegrationTestBase;
import com.inz.praca.repository.ProductRepository;
import com.inz.praca.service.ProductService;
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
		repository.deleteAll();
	}


	@Test
	public void shouldReturn20ProductAscSortByIdWhenSizeArgumentIsEqualTo30() {
		for (int i = 0; i < 30; i++) {
			repository.save(new ProductBuilder().withName("nazwa" + i).withPrice(BigDecimal.ZERO).createProduct());
		}
		List<Product> products = this.productService.getProducts(1, 30, "desc");
		assertThat(products.size()).isEqualTo(20); //taki limit ustalony w ProductService
		Long firstId = products.get(0).getId();
		Long lastId = products.get(products.size() - 1).getId();
		assertThat(firstId > lastId).isTrue();
		assertThat(firstId - lastId).isEqualTo(19L);
	}

	@Test
	public void shouldCreateProductWhenDtoIsCorrect() {
		assertThat(repository.findAll().size()).isEqualTo(0);
		ProductDTO productDTO = new ProductDTO();
		productDTO.setName("nazwa");
		productDTO.setDescription("desc");
		productDTO.setPrice(BigDecimal.TEN);
		productDTO.setImageUrl("url");

		Product product = productService.createProduct(productDTO);

		assertThat(product.getName()).isEqualTo("nazwa");
		assertThat(product.getPrice()).isEqualTo(BigDecimal.TEN);
		assertThat(product.getDescription()).isEqualTo("desc");
		assertThat(product.getImageUrl()).isEqualTo("url");
		assertThat(repository.findAll().size()).isEqualTo(1);
	}



}
