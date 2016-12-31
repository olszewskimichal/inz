package com.inz.praca.integration.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.inz.praca.domain.Product;
import com.inz.praca.builders.ProductBuilder;
import com.inz.praca.integration.JpaTestBase;
import com.inz.praca.repository.ProductRepository;
import org.junit.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

public class ProductRepositoryTest extends JpaTestBase {

	private static final String NAME = "nazwa";
	@Autowired
	private ProductRepository productRepository;

	@Test
	public void shouldFindProductByName() {
		productRepository.deleteAll();
		this.entityManager.persistAndFlush(new ProductBuilder().withName(NAME).withPrice(BigDecimal.TEN).createProduct());
		Optional<Product> byName = this.productRepository.findByName(NAME);
		assertThat(byName).isNotNull();
		assertThat(byName.isPresent()).isTrue();
		assertThat(byName.get().getName()).isEqualTo(NAME);
	}

	@Test
	public void shouldNotFindProductByName() {
		productRepository.deleteAll();
		this.entityManager.persistAndFlush(new ProductBuilder().withName(NAME).withPrice(BigDecimal.TEN).createProduct());
		Optional<Product> byName = this.productRepository.findByName("innaNazwa");
		assertThat(byName).isNotNull();
		assertThat(byName.isPresent()).isFalse();
	}

	@Test
	public void shouldFindAllPageable() {
		productRepository.deleteAll();
		this.entityManager.persist(new ProductBuilder().withName("nazwa1").withPrice(BigDecimal.TEN).createProduct());
		this.entityManager.persist(new ProductBuilder().withName("nazwa2").withPrice(BigDecimal.TEN).createProduct());
		this.entityManager.persist(new ProductBuilder().withName("nazwa3").withPrice(BigDecimal.TEN).createProduct());
		List<Product> content = this.productRepository.findAll(new PageRequest(0, 2)).getContent();
		assertThat(content.size()).isEqualTo(2);
		assertThat(content.get(0).getName()).isEqualTo("nazwa1");
		assertThat(content.get(1).getName()).isEqualTo("nazwa2");

		content = this.productRepository.findAll(new PageRequest(1, 2)).getContent();
		assertThat(content.size()).isEqualTo(1);
		assertThat(content.get(0).getName()).isEqualTo("nazwa3");
	}
}
