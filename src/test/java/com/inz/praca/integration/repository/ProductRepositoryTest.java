package com.inz.praca.integration.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.inz.praca.builders.CategoryBuilder;
import com.inz.praca.builders.ProductBuilder;
import com.inz.praca.domain.Category;
import com.inz.praca.domain.Product;
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
		List<Product> content = this.productRepository.findAllByActive(new PageRequest(0, 2), true).getContent();
		assertThat(content.size()).isEqualTo(2);
		assertThat(content.get(0).getName()).isEqualTo("nazwa1");
		assertThat(content.get(1).getName()).isEqualTo("nazwa2");

		content = this.productRepository.findAllByActive(new PageRequest(1, 2), true).getContent();
		assertThat(content.size()).isEqualTo(1);
		assertThat(content.get(0).getName()).isEqualTo("nazwa3");
	}

	@Test
	public void shouldCountProductByCategory() {
		productRepository.deleteAll();

		Category category = new CategoryBuilder().withName("nazwa1").createCategory();
		Category category2 = new CategoryBuilder().withName("nazwa2").createCategory();
		entityManager.persistAndFlush(category);
		entityManager.persistAndFlush(category2);
		Product product = new ProductBuilder().withName("nazwa").withPrice(BigDecimal.TEN).withCategory("nazwa1").createProduct();
		product.setCategory(category);
		Product product1 = new ProductBuilder().withName("nazwa1").withPrice(BigDecimal.TEN).withCategory("nazwa1").createProduct();
		product1.setCategory(category);
		Product product2 = new ProductBuilder().withName("nazwa2").withPrice(BigDecimal.TEN).withCategory("nazwa2").createProduct();
		product2.setCategory(category2);
		entityManager.persistAndFlush(product);
		entityManager.persistAndFlush(product1);
		entityManager.persistAndFlush(product2);

		List<Object[]> categoryNameAndCountProducts = productRepository.findCategoryNameAndCountProducts();
		assertThat((String) categoryNameAndCountProducts.get(0)[0]).isEqualTo("nazwa1");
		assertThat((Long) categoryNameAndCountProducts.get(0)[1]).isEqualTo(2L);
		assertThat((String) categoryNameAndCountProducts.get(1)[0]).isEqualTo("nazwa2");
		assertThat((Long) categoryNameAndCountProducts.get(1)[1]).isEqualTo(1L);
	}
}
