package com.inz.praca.integration.domain;

import static org.assertj.core.api.Assertions.assertThat;

import javax.persistence.PersistenceException;
import java.math.BigDecimal;

import com.inz.praca.products.ProductBuilder;
import com.inz.praca.category.Category;
import com.inz.praca.products.Product;
import com.inz.praca.integration.JpaTestBase;
import org.junit.Assert;
import org.junit.Test;

public class ProductEntityTest extends JpaTestBase {

	@Test
	public void shouldPersistProductWhenObjectIsCorrect() {
		Product product = entityManager.persistFlushFind(new ProductBuilder().withName("nazwa").withPrice(BigDecimal.TEN).createProduct());
		assertThat(product.getName()).isEqualTo("nazwa");
		assertThat(product.getDescription()).isEqualTo("opis");
		assertThat(product.getPrice().stripTrailingZeros()).isEqualTo(BigDecimal.valueOf(10).stripTrailingZeros());
		assertThat(product.getImageUrl()).isEqualTo("url");
		assertThat(product.getId()).isNotNull();
		assertThat(product.toString()).isNotNull().isNotEmpty().contains("nazwa");
	}

	@Test
	public void shouldPersistProductWithCategory() {
		Category category = entityManager.persistFlushFind(new Category("aaa", "bbb"));
		Product product = entityManager.persistFlushFind(new ProductBuilder().withName("nazwa").withPrice(BigDecimal.TEN).createProduct());
		product.setCategory(category);
		entityManager.persistAndFlush(product);
		Product result = entityManager.find(Product.class, product.getId());
		assertThat(result.getCategory()).isNotNull();
	}

	@Test
	public void shouldNotPersistWithNotUniqueName() {
		try {
			entityManager.persistFlushFind(new ProductBuilder().withName("nazwax12345").withPrice(BigDecimal.TEN).createProduct());
			entityManager.persistFlushFind(new ProductBuilder().withName("nazwax12345").withPrice(BigDecimal.ONE).createProduct());
			Assert.fail();
		}
		catch (PersistenceException e) {
			assertThat(true).isEqualTo(true);
		}

	}

}
